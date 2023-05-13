import java.io.*;
import java.net.*;
import java.net.http.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

import org.w3c.dom.Node;

/**
 * This approach uses the java.net.http.HttpClient classes, which
 * were introduced in Java11.
 */
public class Client {
    private static String hostName;
    private static int portNumber;

    private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    public static void main(String... args) throws Exception {
        if (args.length != 2) {
            System.err.print("java CalcClient <serverAddress> <serverPort>");
            System.exit(1);
        }
        serverAddress = args[0];
        serverPort = Integer.parseInt(args[1]);

        System.out.println(add() == 0);
        System.out.println(add(1, 2, 3, 4, 5) == 15);
        System.out.println(add(2, 4) == 6);
        System.out.println(subtract(12, 6) == 6);
        System.out.println(multiply(3, 4) == 12);
        System.out.println(multiply(1, 2, 3, 4, 5) == 120);
        System.out.println(divide(10, 5) == 2);
        System.out.println(modulo(10, 5) == 0);
    }
    public static int add(Integer...num) throws Exception {
        return RPCRequest("add", num);
    }
    public static int subtract(int lhs, int rhs) throws Exception {
        return RPCRequest("subtract", lhs, rhs);
    }
    public static int multiply(Integer... num) throws Exception {
        return RPCRequest("multiply", num);
    }
    public static int divide(int lhs, int rhs) throws Exception {
        return RPCRequest("divide", lhs, rhs);
    }
    public static int modulo(int lhs, int rhs) throws Exception {
        return RPCRequest("modulo", lhs, rhs);
    }
}

private static int RPCRequest (String mathMethod, Object...params) throws ExceptionIO {
    try {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

        String parameter = "<param>";
        if (arguments.length == 0) {
            parameter += "<param><value><i4>" + 0 + "</i4></value></param>";
        } else {
            for (Object param : arguments) {
                parameter += "<param><value><i4>" + (Integer) param + "</i4></value></param>";
            }
        }
        parameter += "</params>";

        String requestBody = "<?xml version = '1.0'?><methodCall><methodName>" + methodName + "</methodName>" + parameters + "</methodCall>";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://"+host+":"+port+"/RPC"))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header("Content-Type", "text/xml")
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Object[] result = parseResponse(response.body());
        if (result.length == 1) {
            return Integer.valueOf((String)result[0]);
        } else {
            throw new ArithmeticException(result[0] + ", " + result[1]);
        }
        
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return -1;
}
    private static Object[] parseXMLResponse(String responseBody) {
        Object[] result;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(bais);
            doc.getDocumentElement().normalize();

            if (doc.getDocumentElement().getElementsByTagName("fault").getLength() == 0) {
                result = new Object[1];
                result[0] = doc.getElementsByTagName("string").item(0).getTextContent();
                return result;
            } else {
                result = new Object[2];
                String fc = doc.getElementsByTagName("int").item(0).getTextContent();
                String fs = doc.getElementsByTagName("string").item(0).getTextContent();
                result[0] = fc;
                result[1] = fs;
                return result;
            }    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
