package org.example.hw3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;

public class Main {
    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args) {
        Student student1 = new Student("Иван Джейсонович", 19);
        Student student2 = new Student("Петр Иксемелов", 21);
        Student student3 = new Student("Сидр Бинаров", 20);

        student1.setGPA(5.0);
        student2.setGPA(4.3);
        student3.setGPA(4.0);

        try {
            saveStudentToFile("student.json", student1);
            saveStudentToFile("student.xml", student2);
            saveStudentToFile("student.dat", student3);

            Student deserealizedStudent = loadStudentFromFile("student.json");
            System.out.println("Десериализованный из json объект: ");
            System.out.println(deserealizedStudent);

            deserealizedStudent = loadStudentFromFile("student.xml");
            System.out.println("Десериализованный из xml объект: ");
            System.out.println(deserealizedStudent);

            deserealizedStudent = loadStudentFromFile("student.dat");
            System.out.println("Десериализованный из bin объект: ");
            System.out.println(deserealizedStudent);
        }
        catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        }
    }

    public static void saveStudentToFile(String fileName, Student student) throws IOException {
        if(fileName.endsWith(".xml")) {
            xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            xmlMapper.writeValue(new File(fileName), student);
        }
        else if (fileName.endsWith(".json")) {
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(fileName), student);
        }
        else if (fileName.endsWith(".dat")) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                oos.writeObject(student);
            }
        }
    }

    public static Student loadStudentFromFile(String fileName) throws IOException, ClassNotFoundException {
        File inputFile = new File(fileName);
        Student student = new Student();
        if (inputFile.exists()) {
            if (fileName.endsWith(".json")) {
                student = objectMapper.readValue(inputFile, student.getClass());
            }
            else if (fileName.endsWith(".xml")) {
                student = xmlMapper.readValue(inputFile, student.getClass());
            }
            else if (fileName.endsWith(".dat")) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
                     student = (Student) ois.readObject();
                System.out.println("В бинарнике не сохранилось GPA, так как поле указано transient");
            }
        }
        return student;
    }
}