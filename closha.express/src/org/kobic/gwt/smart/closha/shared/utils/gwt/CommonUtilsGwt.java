package org.kobic.gwt.smart.closha.shared.utils.gwt;

import java.util.Date;
import java.util.List;

import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.utils.common.UUID;

public class CommonUtilsGwt {
	
	public static String getDate(){
		Date date = new Date();
		String TheDate = date.toString();
		return TheDate;
	}
	
	public static String getUUID(){
		return UUID.uuid();
	}
	
	public static int changeType(String value){
		return Integer.parseInt(value);
	}
	
	public static String changeType(int value){
		return String.valueOf(value);
	}
	
	public static int getLinkedNetworkPanelWidth(int width){
		return (int)((width * 0.85) * 0.80)-30;
	}
	
	public static int getLinkedNetworkPanelHeight(int height){
		return height - 250;
	}

	public static String getSummarySubString(String summary){
		return summary.substring(0, 50);
	}
	
	public static String getExtension(String path){
		
		int pos = path.lastIndexOf(".");
		
		if(pos == -1){
			return path;
		}
		
		String ext = path.substring(pos + 1);
		
		return ext;
	}
	
	public static boolean chekExtension(String extension){
		for(String str : Constants.EXTENSIONS){
			if(str.equals(extension)){
				return false;
			}
		}
		return true;
	}
	
	public static String imgToTypeConverter(String typeImg){
		
		String convertType = "";
		
		if(typeImg.equals(Constants.DEBUG_IMG)){
			convertType = Constants.DEBUG;
		}else if(typeImg.equals(Constants.COMMENT_IMG)){
			convertType = Constants.COMMENT;
		}else if(typeImg.equals(Constants.ISSUE_IMG)){
			convertType = Constants.ISSUE;
		}
		return convertType;
	}
	
	public static String typeToImgConverter(String type){
		String imgSrc = "";
		
		if(type.equals(Constants.DEBUG)){
			imgSrc = Constants.DEBUG_IMG;
		}else if(type.equals(Constants.COMMENT)){
			imgSrc = Constants.COMMENT_IMG;
		}else if(type.equals(Constants.ISSUE)){
			imgSrc = Constants.ISSUE_IMG;
		}
		return imgSrc;
	}
	
	public static boolean userCheck(String userEmail, String recordEmail){
		return userEmail.equals(recordEmail);
	}
	
	public static String getWorkFlowComleteContent(String projectName, String step){
		
		String content = "A <B>" + projectName + "</B> included in the analysis of the <B>" + step + "</B> module has been completed." +
				"</BR>You can check the results from <B>" + projectName + "</B> project folder</BR>" +
						"-" + getDate();
		
		return content;
	}
	
	public static String getPreferencesLabelContent(int pipelineCount, int runPipelineCount, int 
			completePiplineCount, int waitPipelineCount){
		
		String content = "<p align=\"justify\" style=\"font-size:12px; line-height: 20px\">" + 
				Constants.TAB + "Total pipeline count: " + pipelineCount + Constants.BR + Constants.TAB +  
				"Running pipeline count: " + runPipelineCount + Constants.BR + Constants.TAB +
				"Wait pipeline count: " + waitPipelineCount + Constants.BR + Constants.TAB +
				"Complete pipeline count: " + completePiplineCount + "</p>";
		
		return content;
	}
	
	public static String getAlignJustify(String sentence){
		return "<p align=\"justify\">" + sentence + "<p>";
	}

	public static String getExplorerFont(String sentence){
		return "<font color=\"#996633\">" + sentence + "</font>";
	}
	
	public static String getExplorerFont(String sentence, String color){
		return "<font color=\"" + color + "\">" + sentence + "</font>";
	}
	
	public static String getBold(String sentence){
		return "<b>" + sentence + "</b>";
	}
	
	public static String getStatusImgSrc(int status){
		if(status == 1){
			return "closha/icon/history_running.png";
		}else if(status == 0){
			return "closha/icon/history_wait.png";
		}else if(status == 2){
			return "closha/icon/history_done.png";
		}else if(status == 3){
			return "closha/icon/history_error.png";
		}else{
			return null;
		}
	}
	
	public static String join(List<String> list, String delim, String msg) {
		
		if(list == null) return msg;
		
	    int len = list.size();
	    
	    if(len == 0) return msg;
	        
	    StringBuilder sb = new StringBuilder(list.get(0).toString());
	    for (int i = 1; i < len; i++) {
	        sb.append(delim);
	        sb.append(list.get(i).toString());
	    }
	    return sb.toString();
	}
	
	public static boolean isExtension(String extensions1, String extensions2){
		
		if(extensions1.equals("*")){
			return true;
		}else{
			String tmp_1[] = extensions1.split(",");
			String tmp_2[] = extensions2.split(",");
			
		    for (int i = 0; i < tmp_1.length; i++) {	
		        for (int k = 0; k < tmp_2.length; k++) {
		            if (tmp_1[i].equals(getExtension(tmp_2[k]))) {
		                return true;
		            } 
		        }
		    }
		}
		
		return false;      
	}
}