package com.dsdl.eidea.db.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * Created by 刘大磊 on 2017/4/5 15:19.
 */
public class DbReader {
    public static void main(String[] args) throws  Exception
    {
        String file="G:\\mysql数据库\\201610291620\\201610291620";
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
        for(int i=0;i<1000;i++)
        {

            System.out.println(bufferedReader.readLine());
        }
    }
}
