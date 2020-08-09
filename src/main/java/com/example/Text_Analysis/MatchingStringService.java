package com.example.Text_Analysis;

import com.example.Text_Analysis.model.AnalysisCase;
import com.example.Text_Analysis.model.MatchingString;
import com.example.Text_Analysis.model.UploadForm;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingStringService {
    private File filename = generateUniqueFile();

    //Unique filename for parallel case input at same time
    private File generateUniqueFile() {

        // Explicitly get hold of working directory
        String workingDir = System.getProperty("user.dir");
        String fileSep = System.getProperty("file.separator");
        String directory = "Saved Results";
        String filename = "AnalysisLog.txt";


        String datetime = new Date().toString();
        Random rand = new Random();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");

        int randomNum = rand.nextInt(1000);

        filename = directory + fileSep + filename + "_" + datetime + "_" + randomNum + ".txt";


        new File(workingDir + fileSep + directory).mkdirs();


        File myObj = new File(filename);

        try {
            myObj.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return myObj;

    }


    //return filename
    public File getMyObj() {
        return filename;
    }

    //Read file data as string
    public String loadFromFile(File filename) {
        System.out.println(filename);
        String data = "";
        try {
            Scanner myReader = new Scanner(filename);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine() + " ";
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;

    }

    //Writing to File
    public void writeToFile(List<MatchingString> sortedList, String startingString) {


        try {
            FileWriter myWriter = new FileWriter(filename, true);


            for (MatchingString a : sortedList) {
                myWriter.write(a.toString() + "\n");
            }

            myWriter.write("#\n" + startingString);
            myWriter.write("\n##");
            myWriter.write("\n");
            myWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //String processing for saving
    public List<MatchingString> processUserInput(String userInputManual) {


        //splits string
        String[] splitString = splitString(userInputManual);

        //validates each string
        List<MatchingString> validatedList = analyzeString(splitString);

        //sort string
        List<MatchingString> sortedList = sortResult(validatedList);

        //save to myObj file
        writeToFile(sortedList, userInputManual);

        return sortedList;
    }

    //splits string
    public String[] splitString(String input) {


        String[] splitString = input.split(" ");

        return splitString;
    }

    //validates string array
    public List<MatchingString> analyzeString(String[] input) {


        LinkedList<MatchingString> result = new LinkedList<MatchingString>();
        boolean wasMatched = false;
        String temp;


        for (String a : input) {


            wasMatched = false;

            //checks if latin letter at least 1. No numbers or symbols
            if (!a.matches("[a-zA-Z]{1,}")) {
                continue;
            }

            //for comparison, only lowercase.
            // if list is empty, creates MatchingString object, with the char
            // if list not empty, cycles through checking if char exists
            temp = a.toLowerCase();

            if (result.isEmpty()) {
                result.add(new MatchingString(temp.charAt(temp.length() - 1), a));
            } else {
                for (MatchingString b : result) {

                    if (b.getLetter() == temp.charAt(temp.length() - 1)) {
                        wasMatched = true;
                        b.addMatch(a);
                    }
                }
                if (wasMatched == false)
                    result.add(new MatchingString(temp.charAt(temp.length() - 1), a));
            }
        }

        return result;
    }


    //sorting by char
    public List<MatchingString> sortResult(List<MatchingString> input) {
        return input.stream()
                .sorted(Comparator.comparing(MatchingString::getLetter))
                .collect(Collectors.toList());

    }


    //Read from main file, input history
    public List<AnalysisCase> processHistory() {

        //Lists for holding input and result variations
        List<AnalysisCase> historyList = new ArrayList<AnalysisCase>();
        ArrayList<MatchingString> matchList = new ArrayList<MatchingString>();


        //temporary storage variables
        String testChar;
        char tempChar;
        int tempAmount;
        String tempString;
        String tempStartingString = "";


        try {
            Scanner myReader = new Scanner(filename);

            //main cycle
            while (myReader.hasNextLine()) {


                //checks of result data is finished
                if (myReader.hasNext("#")) {
                    myReader.nextLine();

                    while (!myReader.hasNext("##")) {
                        tempStartingString += myReader.nextLine() + " ";
                    }

                    //clones the finished list and creates instance of main list
                    ArrayList cloneArrayList = (ArrayList) matchList.clone();
                    historyList.add(new AnalysisCase(tempStartingString, cloneArrayList));


                    matchList.clear();
                    tempStartingString = "";

                    myReader.nextLine();
                    continue;
                }


                //reads to variable holder, and wraps to correct format
                testChar = myReader.next();
                tempChar = testChar.charAt(0);
                tempAmount = Integer.parseInt(myReader.next());
                tempString = myReader.nextLine();


                //create an instance from variables
                matchList.add(new MatchingString(tempChar, tempAmount, tempString));


            }


            myReader.close();


        } catch (FileNotFoundException e) {
            System.out.println("Error occured");
            e.printStackTrace();
        }


        return historyList;
    }


    //Upload file to server
    public String doUpload(HttpServletRequest request, UploadForm uploadForm) {

        //Root directory
        String uploadRootPath = request.getServletContext().getRealPath("upload");


        File uploadRootDir = new File(uploadRootPath);
        //create directory if not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = uploadForm.getFileDatas();


        File temp = null;
        for (MultipartFile fileData : fileDatas) {

            //Client file Name
            String name = fileData.getOriginalFilename();

            if (name != null && name.length() > 0) {
                try {
                    //create the file at server
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //
                    temp = serverFile;


                } catch (Exception e) {
                    System.out.println("Error write file: " + name);
                }


            }

        }

        String rez = loadFromFile(temp);


        return rez;

    }

}
