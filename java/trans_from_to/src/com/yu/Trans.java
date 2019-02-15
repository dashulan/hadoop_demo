package com.yu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Trans {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("实验(\\d+|[\u4e00-\u9fa5]){1,2}+");
        Path father = Paths.get("e:","new");
        Path toPlace = Paths.get("d:","All_doc_ed");
        try(Stream<Path> entries = Files.walk(father))
        {
            entries.forEach(a -> {
                System.out.println(a.toString());
                Matcher m = pattern.matcher(a.toString());
                if(m.find() == false){
                    System.out.println("..");
                }else {
                    System.out.println(m.group());
                    String to = m.group().substring(2);
                    System.out.println(to);
                    try {
                        Files.copy(a,Paths.get(toPlace.toString()+"\\"+to,a.getFileName().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

