import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Run {
    private ArrayList<Model> models = new ArrayList<Model>();
    private String result = "";
    int index = 0;
    String currentWord;
    private
    Boolean inputstate = true;
    private Scanner scanner;
    private String[] keyword = {"void", "main", "string", "int", "char", "float", "double", "if", "else", "while", "for", "switch", "case", "break", "return"};
    private String[] specialSymbols = {"+", "-", "*", "/", "%", "&", "!", "=", "==", "<", ">", "<=", ">=", "|", "//", "/*", "*/", ",", "(", ")", "{", "}", "[", "]", "\"", "'", ";", "0", "x",};
    private String[] octal_number = {"0", "1", "2", "3", "4", "5", "6", "7"};
    private String[] hexa_number = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String[] hexa_alpabet = {"a", "b", "c", "d", "e", "f"};
    private int table[][] = {
            // letter(0) , digit(1) , +(2) , -(3) , *(4) , /(5) , %(6) , &(7) , !(8) ,=(9) , <(10) , >(11) , |(12)  , ','(13) , ((14) , )(15) , { , } , [ , ] , "\"" , "'"
            {31, 33, 1, 2, 3, 6, 9, 10, 12, 13, 16, 19, 22, 24, 25, 26, 27, 28, 29, 30, 35, 37, 40, 41, 33, 0},             //Start (0)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_plus (1)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_minus (2)

            {4, 4, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 4},  //_S_multi_in (3) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_multi (4)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_end_remark (5)


            {7, 7, 7, 7, 8, 8, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0, 0, 0, 7},  //_S_divide_in (6) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_divide (7)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //_S_start_remark (8)

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_remainder (9)


            {0, 0, 0, 0, 0, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_and_in (10) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_and (11)

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_Exclamation (12)


            {14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 0, 0, 0, 14},  //_S_equal_in (13) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_equal (14)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_double_equal (15)

            {17, 17, 17, 17, 17, 17, 17, 17, 17, 18, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 0, 0, 0, 17},  //_S_less_in (16) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_less (17)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_less_than_equal (18)

            {20, 20, 20, 20, 20, 20, 20, 20, 20, 21, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 0, 0, 0, 20},  //_S_greater_in (19) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_greater (20)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_greater_than_equal (21)

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_or_in (22) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_or (23)

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_comma (24)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_start_bracket (25)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_end_bracket (26)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_start_brace (27)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_end_brace (28)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_start_brackets (29)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_S_end_brackets (30)

            {31, 31, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 31, 31, 32},  //_G_id_in (31) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_G_id (32)

            {33, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 0, 34},  //_G_number_in (33) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_G_number (34)

            {35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 36, 35, 0, 0, 35, 35},  //_G_String_in (35) ===============
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_G_String (36)


            {37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 38, 0, 0, 37, 37},  //_G_char_in (37) ===========
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_G_char (38)

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_Keyword (39)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //_semicolon (40)

            {0, 41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 43, 42},  //octal_in (41)    ===================
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //octal (42)
            {43, 43, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 44},  //hexa_in (43)     ================
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //hexa (44)

    };

    public Run() {
        scanner = new Scanner(System.in);
        Arrays.sort(specialSymbols);
        Arrays.sort(keyword);
    }

    public void start() {
        out.println("mini c");
        String input_result = "";
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("done")) {
                out.println("----------> " + input_result);
                break;
            } else {
                input_result += input;
            }
        }
        for (int i = 0; i < input_result.length(); i++) {
            Contact.inputString.add(String.valueOf(input_result.charAt(i)));
        }
        int row = 0;
        int column = 0;
        boolean sw = true;
        //while(index != Contact.inputString.size()){
        while (sw) {
            if (Contact.inputString.size() == index) {
                column = 25;
                out.print("마지막");
                out.println("index --------->" + index);
                if (Arrays.binarySearch(specialSymbols, Contact.inputString.get(index - 2)) > 0 && Arrays.binarySearch(specialSymbols, Contact.inputString.get(index - 1)) > 0) {
                    column = 25;
                } else {
                    column = 25;
                    if (result.length() >= 1) {
                        result = result.substring(0, result.length() - 1);
                    }
                }
                sw = false;
            } else if (index <= Contact.inputString.size() - 2) {
                if (Contact.inputString.get(index + 1).equals(" ")) {
                    out.print("공백");
                    if (row == 0) {
                        System.out.println(row);
                        currentWord = Contact.inputString.get(index);
                        System.out.print(Contact.inputString.get(index) + " ");
                        column = getColumn(currentWord);
                        System.out.println("index --------->" + index);
                        index++;
                    } else {
                        if (Arrays.binarySearch(specialSymbols, Contact.inputString.get(index - 1)) > 0 && Arrays.binarySearch(specialSymbols, Contact.inputString.get(index)) > 0) {
                            out.println(row);
                            currentWord = Contact.inputString.get(index);
                            out.print(Contact.inputString.get(index) + " ");
                            column = getColumn(currentWord);
                            out.println("index --------->" + index);
                            index += 2;
                        } else {
                            out.println(row);
                            currentWord = Contact.inputString.get(index);
                            out.print(Contact.inputString.get(index) + " ");
                            column = 25;
                            out.println("index ---------> " + index);
                            index += 2;
                        }
                    }
                } else if (Arrays.binarySearch(specialSymbols, Contact.inputString.get(index)) <= 0 && Arrays.binarySearch(specialSymbols, Contact.inputString.get(index + 1)) > 0) {
                    out.println("other -> special_symbol");
                    if (row == 0) {
                        if (isdigit(Contact.inputString.get(index))) {
                            out.println(row + "");
                            currentWord = Contact.inputString.get(index);
                            out.print(Contact.inputString.get(index) + " ");
                            column = 25;
                            out.println("index --------->" + index);
                            index++;
                        } else {
                            out.println(row + "");
                            currentWord = Contact.inputString.get(index);
                            out.print(Contact.inputString.get(index) + " ");
                            column = getColumn(currentWord);
                            out.println("index --------->" + index);
                            index++;
                        }
                    } else {
                        out.println(row + "");
                        currentWord = Contact.inputString.get(index);
                        out.print(Contact.inputString.get(index) + " ");
                        column = 25;
                        out.println("index --------->" + index);
                        index++;
                    }
                }else {
                    out.println("eeeeeeeeeeeeeeeeeeeeeeeee");
                    currentWord = Contact.inputString.get(index);
                    out.println(Contact.inputString.get(index) + " ");
                    column = getColumn(currentWord);
                    out.println(column + "-++++++++++");
                    out.println("index --------->" + index);
                    index++;
                }
            } else {
                out.println("=============else");
                currentWord = Contact.inputString.get(index);
                out.print(Contact.inputString.get(index) + " ");
                column = getColumn(currentWord);
                out.println("index --------->" + index);
                index++;
            }
            out.println("row &&  column --->" + row + "&&" + column);
            int state = table[row][column];
            out.println("state --->" + state);
            row = getRow(state);
        }

        for (int i = 0; i < models.size(); i++) {
            out.println(models.get(i).getName() + " " + models.get(i).getT_number() + " " + models.get(i).getT_value());
        }
        System.out.println();
        for (int i = 0; i < models.size(); i++) {
            out.println("("+models.get(i).getName() + ") (" + models.get(i).getT_number() + ") (" + models.get(i).getT_value()+")");
        }
        System.out.println();
        System.out.println("================    symbol table   ====================");
        System.out.println("=======   name   =================    attribute   =====");
        for (int i = 0; i < models.size(); i++) {
            if(models.size()>=4){
                if(models.get(i).getT_value().equals("int")||models.get(i).getT_value().equals("string")||models.get(i).getT_value().equals("char")||models.get(i).getT_value().equals("double")||models.get(i).getT_value().equals("float")){
                    if(models.get(i+1).getName().equals("identifier")) {
                        if (models.get(i + 2).getT_number()==8) {
                            if (models.get(i + 3).getName().equals("literal") || models.get(i + 3).getName().equals("string") || models.get(i + 3).getName().equals("char") || models.get(i + 3).getName().equals("literal_octal") || models.get(i + 3).getName().equals("literal_hexa")) {
                                System.out.println("=========== "+models.get(i+1).getT_value()+"  =========== ("+models.get(i).getT_value()+") ("+models.get(i+3).getT_value()+")  ============");
                                i= i+4;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("=======================================================");

        System.out.println();
        System.out.println("================    literal table   ====================");

        for (int i = 0; i < models.size(); i++) {
            if(models.get(i).getName().equals("literal")||models.get(i).getName().equals("literal_octal")||models.get(i).getName().equals("literal_hexa")){
                System.out.println(models.get(i).getT_value());
            }
        }
        System.out.println("=======================================================");
    }

    private int getRow(int state) {
        if (state == 3 || state == 6 || state == 10 || state == 13 || state == 16 || state == 19 || state == 22 || state == 31 || state == 33 || state == 35 || state == 37 || state == 41 || state == 43) {
            result += currentWord;
            System.out.println("result------>" + result);

            return state;
        } else {
            if (state == 32) {
                result += currentWord;
                if (Arrays.binarySearch(keyword, result) > 0) {
                    models.add(new Model("keyword", 25, result));
                    result = "";
                } else {
                    String realResult = result.replaceAll("\\p{Z}","");
                    models.add(new Model("identifier", 26, realResult));
                    result = "";
                }
            } else if (state == 0) {
                result = "";
            } else if (state == 34) {
                result += currentWord;
                String realResult = result.replaceAll("\\p{Z}","");
                models.add(new Model("literal", 27, realResult));
                result = "";
            } else if (state == 36) {
                result = result.substring(1, result.length());
                models.add(new Model("string", 28, result));
                result = "";
            } else if (state == 38) {
                if(result.length()>2){
                    result = result.substring(1, result.length());
                    models.add(new Model("error_char", 29, result));
                    result = "";
                }else {
                    result = result.substring(1, result.length());
                    models.add(new Model("char", 29, result));
                    result = "";
                }
            } else if (state == 1) {
                models.add(new Model("specialSymbol", 1));
                result = "";
            } else if (state == 2) {
                models.add(new Model("specialSymbol", 2));
                result = "";
            } else if (state == 4) {
                models.add(new Model("specialSymbol", 3));
                result = "";
            } else if (state == 5) {
                result += currentWord;
                models.add(new Model("specialSymbol", 16));
                result = "";
            } else if (state == 7) {
                models.add(new Model("specialSymbol", 4));
                result = "";
            } else if (state == 8) {
                result += currentWord;
                models.add(new Model("specialSymbol", 15,result));
                result = "";
            } else if (state == 9) {
                models.add(new Model("specialSymbol", 5));
                result = "";
            } else if (state == 11) {
                models.add(new Model("specialSymbol", 6));
                result = "";
            } else if (state == 12) {
                models.add(new Model("specialSymbol", 7));
                result = "";
            } else if (state == 14) {
                models.add(new Model("specialSymbol", 8));
                result = "";
            } else if (state == 15) {
                models.add(new Model("specialSymbol", 9));
                result = "";
            } else if (state == 17) {
                models.add(new Model("specialSymbol", 10));
                result = "";
            } else if (state == 18) {
                models.add(new Model("specialSymbol", 11));
                result = "";
            } else if (state == 20) {
                models.add(new Model("specialSymbol", 12));
                result = "";
            } else if (state == 21) {
                models.add(new Model("specialSymbol", 13));
                result = "";
            } else if (state == 23) {
                models.add(new Model("specialSymbol", 14));
                result = "";
            } else if (state == 24) {
                models.add(new Model("specialSymbol", 17));
                result = "";
            } else if (state == 25) {
                models.add(new Model("specialSymbol", 18));
                result = "";
            } else if (state == 26) {
                models.add(new Model("specialSymbol", 19));
                result = "";
            } else if (state == 27) {
                models.add(new Model("specialSymbol", 20));
                result = "";
            } else if (state == 28) {
                models.add(new Model("specialSymbol", 21));
                result = "";
            } else if (state == 29) {
                models.add(new Model("specialSymbol", 22));
                result = "";
            } else if (state == 30) {
                models.add(new Model("specialSymbol", 23));
                result = "";
            }else if (state == 40) {
                models.add(new Model("specialSymbol", 24));
                result = "";
            } else if (state == 42) {
                result += currentWord;
                for(int i=0;i<result.length();i++){

                    if(i<result.length()-2&&!Arrays.asList(octal_number).contains(String.valueOf(result.charAt(i+1)))){
                        models.add(new Model("error_number_octal", 27, result));
                        result = "";
                        return 0;
                    }
                }
                models.add(new Model("literal_octal", 27, result));
                result = "";
            } else if (state == 44) {
                result += currentWord;
                for(int i=2;i<result.length();i++){
                    if(isdigit(String.valueOf(result.charAt(i)))){
                        if(!Arrays.asList(hexa_number).contains(String.valueOf(result.charAt(i)))){
                            System.out.println("error -----> number "+String.valueOf(result.charAt(i)));
                            models.add(new Model("error_literal_hexa", 27, result));
                            result = "";
                            return 0;
                        }
                    }else if(!isdigit(String.valueOf(result.charAt(i)))){
                        if(!Arrays.asList(hexa_alpabet).contains(String.valueOf(result.charAt(i)))){
                            System.out.println("error -----> alpa "+String.valueOf(result.charAt(i)));
                            models.add(new Model("error_literal_hexa", 27, result));
                            result = "";
                            return 0;
                        }
                    }
                }
                models.add(new Model("literal_hexa", 27, result));
                result = "";
            }
            out.println("Acc ---->" + state);
            return 0;
        }
    }

    private int getColumn(String msg) {
        if (isdigit(msg)) {
            if (msg.equals("0")) {
                return 23;
            }
            return 1;
        } else if (msg.equals("+")) {
            return 2;
        } else if (msg.equals("-")) {
            return 3;
        } else if (msg.equals("*")) {
            return 4;
        } else if (msg.equals("/")) {
            return 5;
        } else if (msg.equals("%")) {
            return 6;
        } else if (msg.equals("&")) {
            return 7;
        } else if (msg.equals("!")) {
            return 8;
        } else if (msg.equals("=")) {
            return 9;
        } else if (msg.equals("<")) {
            return 10;
        } else if (msg.equals(">")) {
            return 11;
        } else if (msg.equals("|")) {
            return 12;
        } else if (msg.equals(",")) {
            return 13;
        } else if (msg.equals("(")) {
            return 14;
        } else if (msg.equals(")")) {
            return 15;
        } else if (msg.equals("{")) {
            return 16;
        } else if (msg.equals("}")) {
            return 17;
        } else if (msg.equals("[")) {
            return 18;
        } else if (msg.equals("]")) {
            return 19;
        } else if (msg.equals("\"")) {
            return 20;
        } else if (msg.equals("'")) {
            return 21;
        } else if (msg.equals(";")) {
            return 22;
        } else if (msg.equals(" ")) {
            return 25;
        } else {
            if (result.equals("0")&&Contact.inputString.get(index).equals("x")) {
                return 24;
            }
            return 0;
        }
    }

    private boolean isdigit(String msg) {
        try {
            Integer.parseInt(msg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}