package service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet("download/*")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String file = cutFileName(request);
        String path = "E:\\Projects\\servlet-task\\src\\main\\filepath\\";

        configFile(response, file);

        sendFile(response, file, path);
    }

    private void configFile(HttpServletResponse response, String file) {
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setContentType("text/html");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");
    }

    private void sendFile(HttpServletResponse response, String file, String path) throws IOException {
        PrintWriter out = response.getWriter();
        FileInputStream fileInputStream = new FileInputStream(path + file);
        int i;
        while ((i = fileInputStream.read()) != -1) {
            out.write(i);
        }
        fileInputStream.close();
        out.close();
    }

    private String cutFileName(HttpServletRequest request){
        String uri = request.getRequestURI();
        String[] uriContent = uri.split("\\/+");
        String fileName = uriContent[uriContent.length - 1];
        return fileName;
    }
}