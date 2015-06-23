package org.myorg.proj;
import java.io.*;

public class NaiveMerger
{

	public static String[] mergefn(String str1,String str2)
	{		
		String[] temp1=str1.split("#");
		String[] temp2=str2.split("#");
		//System.out.println(temp1.length+" and "+temp2.length);
		String[] tempx=new String[temp1.length+temp2.length-2];
		int f;
		for(f=0;f<temp1.length-1;f++)
		{
			tempx[f]=temp1[f];
		}
		//System.out.println("The f value is :"+f +"and the temp2 length is :"+temp2.length);
		//System.out.println(tempx.length);		
		for(int j=0;j<temp2.length-1;j++,f++)
		{
			tempx[f]=temp2[j];
		}
		System.out.println();
		int K=Integer.parseInt(temp1[temp1.length-1]);
		String[] z=new String[K];
		for(int i=0;i<K;i++)
		{
			z[i]="";
		}		
		for(int i=0;i<tempx.length;i++)
		{
			String newstr=tempx[i];
			//System.out.println(newstr);
			String[] temp=newstr.split("@");
			String newtemp=temp[2]+"@"+temp[0];
			int x=Integer.parseInt(temp[1]);
			z[x]=z[x]+newtemp+"#";
		}
		System.out.println();
		/*for(int i=0;i<K;i++)
		{
			System.out.println();
			System.out.println(z[i]);
		}*/
		return z;
	}

}
