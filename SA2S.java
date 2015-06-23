package org.myorg.proj;
import java.io.*;

public class SA2S
{
	public String func(String[] z)
        {
                //int l=z.length();
                String temp="";
                for(int i=0;i<z.length;i++)
                {
                        String[] temp2=z[i].split("#");
                        for(int j=0;j<temp2.length;j++)
                        {
                                String[] temp3=temp2[j].split("@");
                                temp=temp+temp3[1]+i+temp3[0]+"#";
                        }
                }
                return temp;
        }
}
