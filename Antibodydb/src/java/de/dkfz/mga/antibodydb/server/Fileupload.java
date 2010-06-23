package de.dkfz.mga.antibodydb.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Fileupload extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/plain");

    if (req.getMethod().equalsIgnoreCase("post")) {
      FileItem uploadItem = getFileItem(req);
      if (uploadItem == null) {
        resp.getWriter().write("NO-SCRIPT-DATA");
        return;
      }

      byte[] fileContents = uploadItem.get();

      resp.getWriter().write(uploadItem.getName());
    }
  }


  private FileItem getFileItem(HttpServletRequest request) {
    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    try {
      List items = upload.parseRequest(request);
      Iterator it = items.iterator();
      while (it.hasNext()) {
        FileItem item = (FileItem) it.next();
        if (!item.isFormField() && "file".equals(item.getFieldName())) {
          return item;
        }
      }
    } catch (FileUploadException e) {
      return null;
    }
    return null;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doPost(req, resp);
  }

}
