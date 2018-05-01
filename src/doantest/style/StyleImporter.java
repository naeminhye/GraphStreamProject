package doantest.style;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  Đọc file css
 */
public class StyleImporter { 
    
    /**
     * 
     * @param stylesheet: đường dẫn tới file css
     * @return 
     */
    public static String getStyle(String stylesheet)
    {		
            InputStream input;
            BufferedReader reader;
            StringBuilder output;

            // Initialize Streams and Readers
            try{
                    input = StyleImporter.class.getResourceAsStream(stylesheet);
            } catch(Exception e) { e.printStackTrace(); return null; }

            try{
                    reader = new BufferedReader(new InputStreamReader(input));
            } catch(Exception e) { e.printStackTrace(); return null; }

            // Build String
            try{
                    output = new StringBuilder();

                    // Create String
                    String line;
                    while ((line = reader.readLine()) != null) {
                            output.append(line);
                    }
            }
            catch (Exception e) { e.printStackTrace(); return null; }

            // Close Reader
            finally
            {
                    try{
                            reader.close();
                    }
                    catch(Exception e)
                    { e.printStackTrace(); return null; }
            }

            return output.toString();
    }
}
