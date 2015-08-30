
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class jobideraser {
    ArrayList wytyczne[] = new ArrayList[7]; //0-6:equals,startsWith,contains,endsWith,NOTStartsWith,NOTContains,NOTEndsWith
    String erasedJobID;
    
    public jobideraser () {       
        for (int j=0;j<7;j++) wytyczne[j] = new ArrayList();
        try {
            BufferedReader config = new BufferedReader(new FileReader("EraserConfig.txt"));
            String result; int index=-1;
            do {
                result = config.readLine(); if (result==null) break;
                if (result.split(":").length>1) index++;
                else {
                    if (index<0) erasedJobID=result.split("=")[1];
                    else {
                        String results[] = result.split("#");
                        for (int i=0;i<results.length;i++) wytyczne[i].add(results[i]);
                    }
                }
            } while (result!=null);
            
            File workDirectory = new File(".");
            for (int i=0;i<workDirectory.listFiles().length;i++) {
                File actualDirectory = workDirectory.listFiles()[i];
                if (actualDirectory.isDirectory()) for (int j=0;j<wytyczne[0].size();j++) {
                    if (!wytyczne[0].get(j).toString().equals("!") && 
                        !actualDirectory.getName().equals(wytyczne[0].get(j).toString())) continue;
                    if (!wytyczne[1].get(j).toString().equals("!") && 
                        !actualDirectory.getName().startsWith(wytyczne[1].get(j).toString())) continue;
                    if (!wytyczne[2].get(j).toString().equals("!") && 
                        !actualDirectory.getName().contains(wytyczne[2].get(j).toString())) continue;
                    if (!wytyczne[3].get(j).toString().equals("!") && 
                        !actualDirectory.getName().endsWith(wytyczne[3].get(j).toString())) continue;
                    if (!wytyczne[4].get(j).toString().equals("!") && 
                        actualDirectory.getName().startsWith(wytyczne[4].get(j).toString())) continue;
                    if (!wytyczne[5].get(j).toString().equals("!") && 
                        actualDirectory.getName().contains(wytyczne[5].get(j).toString())) continue;
                    if (!wytyczne[6].get(j).toString().equals("!") && 
                        actualDirectory.getName().endsWith(wytyczne[6].get(j).toString())) continue;
                    File[] actualDirectoryList = actualDirectory.listFiles();
                    for (int k=0;k<actualDirectoryList.length;k++)  {
                        String actFileName = actualDirectoryList[k].getName();
                        String[] splitted = actFileName.split("_");
                        if (splitted.length>1) if (splitted[0].startsWith("j-")) {
                            File newName = new File(actualDirectory.getAbsolutePath()+"/j-"+erasedJobID+actualDirectoryList[k].getName().substring(splitted[0].length()));
                            actualDirectoryList[k].renameTo(newName);   
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new jobideraser();
    }
    
}
