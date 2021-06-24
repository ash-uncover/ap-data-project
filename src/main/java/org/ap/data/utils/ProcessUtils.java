package org.ap.data.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {

	public static List<String> runProcess(ProcessBuilder pb) throws Exception {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		List<String> result = new ArrayList<String>();
		try {
			// Run command
			Process process = pb.start();
			// Read output
			is = process.getInputStream();
	        isr = new InputStreamReader(is);
	        br = new BufferedReader(isr);
	        
	        String line = null;
	        while ( (line = br.readLine()) != null) {
	           result.add(line);
	        }
	        process.waitFor();
	        return result;
	        
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				try { is.close(); } catch (Exception e) {}
			}
			if (isr != null) {
				try { isr.close(); } catch (Exception e) {}
			}
			if (br != null) {
				try { br.close(); } catch (Exception e) {}
			}
		}
	}
}
