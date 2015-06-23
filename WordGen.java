package org.myorg.proj;
import java.io.*;
import java.util.*;

public class WordGen {
static int hmlength=0;	
public static int[][] wordListGen (String docdir) {

 	//String docdir="docdir_op/";
        File file = new File(docdir);
 	int i=0,j,x,count=0;
	String[] filelist=file.list();
	System.out.println(filelist.length);

	HashMap hm = new HashMap();
	String[][] wordlist=new String[filelist.length][];
	int[][] documents=new int[wordlist.length][];

	List<String> lst = new ArrayList<String>();
	List<String> lst1 = new ArrayList<String>();
        int[] windoc=new int[filelist.length];
        try {
 	    for(x=0;x<filelist.length;x++)
	    {
		windoc[x]=0;
	    File newfile=new File(docdir+filelist[x]);
	    System.out.println("The file name is : "+newfile.getName());
   	    Scanner scanner = new Scanner(newfile);
            //Scanner scanner1 = new Scanner(newfile);
 	    //while(scanner.hasNext()) 
		//count=count+1;
	    //System.out.println(count);
	    //wordlist[filelist.length]=new String[count];	
            while (scanner.hasNext()) {
                //wordlist [0][i] = scanner.next();
		lst.add(scanner.next());                
		System.out.println(lst.get(i));
		//wordlist[x][j]=lst.get(i);		
		i++;
		windoc[x]++;
		
            }
            scanner.close();
	}
		int new_i=0;
	    for(x=0;x<filelist.length;x++)
	    {
		int my=0;
		File newfile=new File(docdir+filelist[x]);
	        //System.out.println("The file name is : "+newfile.getName());
   	       Scanner scanner1 = new Scanner(newfile);
            wordlist[x]=new String[windoc[x]];	
            while (scanner1.hasNext()) {
                //wordlist [0][i] = scanner.next();
		lst1.add(scanner1.next());                
		//System.out.println(lst.get(i));
		wordlist[x][my]=lst1.get(new_i);		
		new_i++;
		my++;
		
            }
	scanner1.close();
    }
}
catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	/*for ( i=0;i<5;i++)
	{
		for (j=0;j<wordlist[i].length;j++)
		{
			wordlist[i+1][j] = wordlist[i][j];
		}
	}*/
			
	/*String [][] wordlist = { {"hi", "hello", "apple", "ball", "cat", "cat"},
            {"hi","hi","hi","hello","apple"},
            {"apple","cat","cat","ball","cat"},
            {"apple","cat"},
            {"ball","ball","ball"}};*/

	/*hm.put("hi", new Integer(0));
	hm.put("hello", new Integer(1));
	hm.put("apple", new Integer(2));
	hm.put("ball", new Integer(3));
	hm.put("cat", new Integer(4));*/
	//Set set=hm.entrySet();
	//Iterator i=set.Iterator();
	int s=0;
	//int i,j;
	for(i=0;i<wordlist.length;i++)
	{
		documents[i] = new int[wordlist[i].length];
		for(j=0;j<wordlist[i].length;j++)
		{
			if(hm.containsKey(wordlist[i][j]) == false)
			{
			hm.put(wordlist[i][j],new Integer(s));
			s++;
			}
			Object v=hm.get(wordlist[i][j]);			
			documents[i][j]=Integer.parseInt(v.toString());	
			//System.out.println(documents[i][j]);
		}
	}
	hmlength=hm.size();
	//System.out.println("The hash map length is : "+hmlength);
	return documents;
}
}

