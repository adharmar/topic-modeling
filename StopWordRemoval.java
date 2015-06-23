package org.myorg.proj;
import java.io.*;
import java.util.*;

public class StopWordRemoval
    {
     public static void replace(String ip_file)
         {
         try
             {
             //File filed = new File("helloworld.txt");
	     BufferedReader reader = new BufferedReader(new FileReader(ip_file));
	     Scanner sc=new Scanner(new FileReader("stopwords.txt"));
	     Scanner sc_spl=new Scanner(new FileReader("nonalpha.txt"));

		String s="";
		
             String line = "", oldtext = "";
             while((line = reader.readLine()) != null)
                 {
                 oldtext += line + "\r\n";
             }
	     oldtext=oldtext.toLowerCase();
             reader.close();
             // replace a word in a file
             //String newtext = oldtext.replaceAll("drink", "Love");
            
             //To replace a line in a file
//String newtext="";
//System.out.println(ip_file);
String[] temp=ip_file.split("/");
//String[] temp2=temp[1].split(".");
//System.out.println(temp2[1]);
String op="docdir_op/"+temp[1];//+"_op.txt";
//System.out.println(op);
             FileWriter writer = new FileWriter(op);
		while(sc.hasNext())
		{
		s=sc.next();
		s=" "+s+" ";
		//System.out.println(s);
		//do whatever you want with s
		
              oldtext = oldtext.replaceAll(s," ");

            
}
		while(sc_spl.hasNext())
		{
		s=sc_spl.next();
		oldtext=oldtext.replaceAll(s,"");
		}
             writer.write(oldtext);
writer.close();
sc.close();
         }
         catch (IOException ioe)
             {
             ioe.printStackTrace();
         }
//return "docdir_op/";
     }
}
