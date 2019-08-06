package cn.com.lilu.file.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FormFileUploadServlet extends HttpServlet{

	// 上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB

    private static final long serialVersionUID = 1L;

    private static final Log logger = LogFactory.getLog(FormFileUploadServlet.class);

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, Object> outArgMap = new HashMap<String, Object>();

        // 文件成功数量
        int fileNumber = 0;
        
        try
        {
            // 检测是否为多媒体上传
//            if (!ServletFileUpload.isMultipartContent(request))
//            {
//                // 如果不是则停止
//                logger.debug("表单必须包含 enctype=multipart/form-data");
//                return;
//            }

            //存放目标文件目录
            String destDir = request.getParameter("filePath");
            String isRelativePath = request.getParameter("isRelativePath");
            String append = request.getParameter("append");
            if (destDir == null)
            {
                destDir = "/JSSDKPLUGIN";
            }
            if ("true".equalsIgnoreCase(isRelativePath))
            {
                destDir = new File(
                        destDir).getAbsolutePath();
            }

            // 设置临时目录
            File tmpFile = new File(destDir, "temp");
            if (!tmpFile.exists())
            {
                tmpFile.mkdir();
            }
            // 配置上传参数
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            // 设置临时存储目录
            factory.setRepository(tmpFile);
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 中文处理
            upload.setHeaderEncoding("UTF-8");
            // 解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && !formItems.isEmpty())
            {
                // 迭代表单数据
                for (FileItem item : formItems)
                {
                    String fieldName = item.getFieldName();
                    // 处理不在表单中的字段
                    if (!item.isFormField() && "file".equals(fieldName))
                    {
                        // 在这里设置并创建一下路径
                        File uploadDir = new File(destDir);
                        if (!uploadDir.exists())
                        {
                            uploadDir.mkdirs();
                        }
                        logger.debug("文件开始上传：uploadDir = " + uploadDir.getAbsolutePath());
                        
                        String fileName = item.getName();
                        // 以防文件上传输入框为空
                        if (fileName == null || fileName.isEmpty())
                        {
                            continue;
                        }
                        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                        
                        File storeFile = new File(uploadDir, fileName);
                        try (InputStream in = item.getInputStream();
                                OutputStream out = new FileOutputStream(
                                        storeFile, "true".equals(append)))
                        {
                            byte b[] = new byte[8192];
                            int len = -1;
                            while ((len = in.read(b)) != -1)
                            {
                                out.write(b, 0, len);
                            }
                        } catch (IllegalStateException | IOException e)
                        {

                            logger.error("文件上传失败！！！ ", e);
                            outArgMap.put("Msg", "文件上传失败！！！ "
                                    + e.getMessage().replace("\\", "/"));
                            return;
                        }
                        String success = String.format(
                                "文件上传成功：storeFile = %s,上传大小：size = %s字节",
                                storeFile.getAbsolutePath(), item.getSize());
                        logger.debug(success);
                        outArgMap.put("Msg" + ++fileNumber, success.replace("\\", "/"));
                        // 删除文件上传时产生的临时文件
                        item.delete();
                    }
                }
            }
        } catch (Exception e)
        {
            logger.error("文件上传失败！！！ ", e);
            outArgMap.put("Msg",
                    "文件上传失败！！！ " + e.getMessage().replace("\\", "/"));
            return;
        }
        
        logger.debug("成功上传文件:" + fileNumber + "个");
        return;
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request
     *        servlet request
     * @param response
     *        servlet response
     * @throws ServletException
     *         if a servlet-specific error occurs
     * @throws IOException
     *         if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request
     *        servlet request
     * @param response
     *        servlet response
     * @throws ServletException
     *         if a servlet-specific error occurs
     * @throws IOException
     *         if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

}
