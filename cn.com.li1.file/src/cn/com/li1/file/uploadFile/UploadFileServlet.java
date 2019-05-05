/**
 * 
 */
package cn.com.li1.file.uploadFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.agree.ab.a4.pub.runtime.ABPlatform;

/**
 * @author huang.dy
 * 
 */
public class UploadFileServlet extends HttpServlet
{

    /**
     * 
     */
    private static final long serialVersionUID = 63247623846237647L;

    private static final Log logger = LogFactory
            .getLog(UploadFileServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        String fileName = req.getParameter("filePath");
        if (fileName == null || fileName.trim().length() == 0)
        {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String append = req.getParameter("append");
        String isRelativePath = req.getParameter("isRelativePath");
        InputStream is = req.getInputStream();
        OutputStream os = null;
        // 判断是不是相对路径
        if ("true".equalsIgnoreCase(isRelativePath))
        {
            fileName = new File(
                    ABPlatform.getConfigurationLocation().getParentFile(),
                    fileName).getAbsolutePath();
        }
        
        // 是不是正确的文件路径
        if (fileName.lastIndexOf(File.separatorChar) == -1
                || !fileName.substring(fileName.lastIndexOf(File.separatorChar))
                        .contains("."))
        {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        File file = new File(fileName);
        // 不存在目录时先创建
        if (!file.exists())
        {
            File uploadDir = file.getParentFile();
            if (!uploadDir.isDirectory())
            {
                uploadDir.mkdirs();
                file.createNewFile();
            }
        }
        try
        {
            os = new FileOutputStream(file, "true".equals(append));
            byte[] bytes = new byte[4096];
            int count = 0;
            while ((count = is.read(bytes)) != -1)
            {
                os.write(bytes, 0, count);
            }
        } catch (Exception e)
        {
            logger.error("接收上传的文件[" + fileName + "]失败", e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                } catch (IOException e)
                {
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                }
            }
        }
    }
}
