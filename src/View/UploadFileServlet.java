package View;

import Controller.CSVController;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import static java.lang.System.out;

/**
 * Servlet that manages file upload to server and later process by CSV controller
 */
@WebServlet(name = "UploadFileServlet")
public class UploadFileServlet extends HttpServlet {

    private boolean isMultipart;
    private File file ;
    private static String TEMP_DIR = "/tmp/";
    private String newRandomName;
    private CSVController controller;

    /**
     * manages HTTP post request with file
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        isMultipart = ServletFileUpload.isMultipartContent(request);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File("/tmp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        /* if it's a file, not a form */
        if(isMultipart) {

            controller = CSVController.getInstance();

            // Parse the request to get file items.
            List fileItems;
            File uploadedFile;
            try {

                fileItems = upload.parseRequest(request);
                // Process the uploaded file items
                Iterator i = fileItems.iterator();

                while (i.hasNext()) {
                    FileItem fi = (FileItem) i.next();

                    // if a file is uploaded (not a form field)
                    if (!fi.isFormField()) {
                        // Get the uploaded file parameters
                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();

                        newRandomName = generateRandomName(fileName);

                        uploadedFile = new File(TEMP_DIR + newRandomName);

                        fi.write(uploadedFile);

                        RequestDispatcher requestDispatcher;
                        requestDispatcher = request.getRequestDispatcher("/ImportCSVFile.jsp");

                        /* insert records in DB */
                        if(controller.processCSVDataFile(uploadedFile)){
                            request.setAttribute("upload","OK");

                        } else {
                            request.setAttribute("upload","FAILED");
                        }
                        requestDispatcher.forward(request, response);

                    }
                }

            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private String generateRandomName(String fileName) {

        //TODO RANDOM NAME
        return fileName;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
