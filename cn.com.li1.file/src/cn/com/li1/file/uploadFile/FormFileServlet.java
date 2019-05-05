package cn.com.li1.file.uploadFile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.agree.ab.a4.pub.runtime.ABPlatform;

public class FormFileServlet extends HttpServerServlet
{
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        // 读取请求Body
        byte[] body = readBody(request);
        // 取得所有Body内容的字符串表示

        String textBody = new String(body, "gbk");
        // 取得上传的文件名称
        String fileName = getFileName(textBody);
        // 取得文件开始与结束位置
        Position p = getFilePosition(request, textBody);

        // 输出至文件
        writeTo(request, fileName, body, p);
    }

    // 构造类
    class Position
    {
        int begin;

        int end;

        public Position(int begin, int end)
        {
            this.begin = begin;
            this.end = end;
        }
    }

    private byte[] readBody(HttpServletRequest request) throws IOException
    {
        // 获取请求文本字节长度
        int formDataLength = request.getContentLength();
        // 取得ServletInputStream输入流对象
        ServletInputStream inputStream = request.getInputStream();
        DataInputStream dataStream = new DataInputStream(inputStream);
        byte body[] = new byte[formDataLength];
        int totalBytes = 0;
        while (totalBytes < formDataLength)
        {
            int bytes = dataStream.read(body, totalBytes, formDataLength);
            totalBytes += bytes;
        }
        return body;
    }

    private Position getFilePosition(HttpServletRequest request,
            String textBody) throws IOException
    {
        // 取得文件区段边界信息
        String contentType = request.getContentType();
        String boundaryText = contentType.substring(
                contentType.lastIndexOf("=") + 1, contentType.length());
        // 取得实际上传文件的气势与结束位置
        int pos = textBody.indexOf("filename=\"");
        pos = textBody.indexOf("\n", pos) + 1;
        pos = textBody.indexOf("\n", pos) + 1;
        pos = textBody.indexOf("\n", pos) + 1;
        int boundaryLoc = textBody.indexOf(boundaryText, pos) - 4;
        int begin = ((textBody.substring(0, pos)).getBytes("gbk")).length;
        int end = ((textBody.substring(0, boundaryLoc)).getBytes("gbk")).length;

        return new Position(begin, end);
    }

    private String getFileName(String requestBody)
    {
        String fileName = requestBody
                .substring(requestBody.indexOf("filename=\"") + 10);
        fileName = fileName.substring(0, fileName.indexOf("\n"));
        fileName = fileName.substring(fileName.indexOf("\n") + 1,
                fileName.indexOf("\""));

        return fileName;
    }

    private void writeTo(HttpServletRequest request,String fileName, byte[] body, Position p)
            throws IOException
    {
        String isRelativePath = request.getParameter("isRelativePath");
        String filePath = request.getParameter("filePath");
        String append = request.getParameter("append");
        if (filePath == null)
        {
            filePath = "/JSSDKPLUGIN";
        }
        if ("true".equalsIgnoreCase(isRelativePath))
        {
            filePath = new File(
                    ABPlatform.getConfigurationLocation().getParentFile(),
                    filePath).getAbsolutePath();
        }
        File uploadDir = new File(filePath);
        if (!uploadDir.isDirectory())
        {
            uploadDir.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(
                filePath + File.separatorChar + fileName,
                "true".equals(append));
        fileOutputStream.write(body, p.begin, (p.end - p.begin));
        fileOutputStream.flush();
        fileOutputStream.close();
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }
}
