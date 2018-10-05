import java.sql.*;
import java.io.*;

 
public class photos 
{
        public static void main(String[] args) 
        {
                DB db = new DB();
                Connection conn=db.dbConnect(
                  "jdbc:jtds:sqlserver://localhost:1433/vinayagamission","sa","");
                //db.insertImage(conn,"d://filepath//test.JPG");
                db.getImageData(conn);
        }
 
}
 
class DB
{
        public DB() {}
 
        public Connection dbConnect(String db_connect_string,
           String db_userid, String db_password)
        {
                try
                {
                        Class.forName("net.sourceforge.jtds.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(
                          db_connect_string, db_userid, db_password);
 
                        System.out.println("connected");
                        return conn;
                         
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        return null;
                }
        }
 
//        public void insertImage(Connection conn,String img)
//        {
//                int len;
//                String query;
//                PreparedStatement pstmt;
//                 
//                try
//                {
//                        File file = new File(img);
//                        FileInputStream fis = new FileInputStream(file);
//                        len = (int)file.length();
// 
//                        query = ("insert into TableImage VALUES(?,?,?)");
//                        pstmt = conn.prepareStatement(query);
//                        pstmt.setString(1,file.getName());
//                        pstmt.setInt(2, len);
//   
//                        // Method used to insert a stream of bytes
//                        pstmt.setBinaryStream(3, fis, len); 
//                        pstmt.executeUpdate();
// 
//                }
//                catch (Exception e)
//                {
//                        e.printStackTrace();
//                }
//        }
 
        public void getImageData(Connection conn)
        {
                 
                 byte[] fileBytes;
                 String query;
                 try
                 {
                         query = " select member_code,picture from member_mas where lock='CLEARANCE' and fileLength!='0' ";
                         Statement state = conn.createStatement();
                         ResultSet rs = state.executeQuery(query);
                         while (rs.next())
                        {
                        	 
                        	 
                        	 StringBuffer fileName = new StringBuffer();
                        	 fileName = fileName.append("d://filepath//NOT CLEARANCE//");
                        	 fileName = fileName.append(rs.getString("member_code"));
                        	 fileName = fileName.append(".jpg");
                        	 
                        	 String path = fileName.toString();
                        	 
                        	 System.out.println(":::::::path::::::::::"+path);
                        	 
                                  fileBytes = rs.getBytes("picture");
                                  OutputStream targetFile=  
                                  new FileOutputStream(path);
 
                                  targetFile.write(fileBytes);
                                  targetFile.close();
                        }        
                         
                 }
                 catch (Exception e)
                 {
                         e.printStackTrace();
                 }
        }
};