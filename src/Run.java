import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.util.Arrays;
import java.util.Scanner;

public class Run {

    //test
    int index= 0;String currentWord ;
    private
     Boolean inputstate = true;
    private Scanner scanner;
    private String [] keyword = {"void","int","char","float","double","if","else","while","for","switch","case","break","return"};
    private String [] specialSymbols = {"+","-","*","/","%","&&","!","=","==","<",">","<=",">=","||","//","/*","*/",",","(",")","{","}","[","]"};
    private int table[][] = {
            // letter(0) , digit(1) , +(2) , -(3) , *(4) , /(5) , %(6) , &(7) , !(8) ,=(9) , <(10) , >(11) , |(12)  , ','(13) , ((14) , )(15) , { , } , [ , ]
            {31,33,1,2,3,6,9,10,12,13,16,19,22,24,25,26,27,28,29,30,0},             //Start (0)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_plus (1)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_minus (2)

            {4,4,4,4,4,5,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,0},  //_multi_in (3) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_multi (4)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_end_remark (5)


            {7,7,7,7,8,8,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,0},  //_S_divide_in (6) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_divide (7)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //_S_start_remark (8)

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_remainder (9)


            {0,0,0,0,0,0,0,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_and_in (10) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_and (11)

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_Exclamation (12)


            {14,14,14,14,14,14,14,14,14,15,14,14,14,14,14,14,14,14,14,14,14,14},  //_S_equal_in (13) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_equal (14)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_double_equal (15)

            {17,17,17,17,17,17,17,17,17,18,17,17,17,17,17,17,17,17,17,17,17,17},  //_S_less_in (16) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_less (17)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_less_than_equal (18)

            {20,20,20,20,20,20,20,20,20,21,20,20,20,20,20,20,20,20,20,20,20,20},  //_S_greater_in (19) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_greater (20)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_greater_than_equal (21)

            {0,0,0,0,0,0,0,0,0,0,0,0,23,0,0,0,0,0,0,0,0,0},  //_S_or_in (22) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_or (23)

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_comma (24)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_start_bracket (25)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_end_bracket (26)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_start_brace (27)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_end_brace (28)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_start_brackets (29)
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_S_end_brackets (30)

            {31,31,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,32},  //_G_id_in (31) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_G_id (32)

            {33,33,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,34},  //_G_number_in (33) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_G_number (34)

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_G_String_in (35) ===============
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_G_String (36)

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_G_char (37)

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //_Keyword (38)

    };
    public Run (){
        scanner = new Scanner(System.in);
    }

    public void start(){
        System.out.println("mini c");
        String result = "";
        while (true) {
            String input = scanner.nextLine();
            if(input.equals("done")) {
                System.out.println("----------> " +result);
                break;
            }else {
                result += input;
            }
        }

        for(int i=0; i<result.length();i++){
            Contact.inputString.add(String.valueOf(result.charAt(i)));
        }

        int row = 0;
        boolean sw = true;
        //while(index != Contact.inputString.size()){
        while(sw){
            if(index != Contact.inputString.size()) {
                currentWord = Contact.inputString.get(index);
                System.out.println(Contact.inputString.get(index));
                int column = getColumn(currentWord);
                System.out.println("row &&  column --->" + row + "&&" + column);
                int state = table[row][column];
                System.out.println("state --->" + state);
                row = getRow(state);

                index++;
            }
        }

       /* String[] split  = result.split(" ");
        for(int i=0; i<split.length;i++){
            Contact.inputString2.add(split[i]);
        }
        for(int i=0;i<Contact.inputString2.size();i++){
            //System.out.println(Contact.inputString2.get(i));

        }*/

    }
    private int getRow(int state){
        if (state==3 ||state==6 || state==10 ||state==13|| state==16|| state==19|| state==22|| state==31|| state==33|| state==35){
            return state;
        }else {
            System.out.println("Acc ---->" + state);
        }return 0;
    }
    private int getColumn (String msg){
        if (isdigit(msg)){
            return 1;
        }else if(msg.equals("+")){

            return  2;
        }else if(msg.equals("-")){
            return  3;
        }else if(msg.equals("*")){
            return  4;
        }else if(msg.equals("/")){
            return  5;
        }else if(msg.equals("%")){
            return  6;
        }else if(msg.equals("&")){
            return  7;
        }else if(msg.equals("!")){
            return  8;
        }else if(msg.equals("=")){
            return  9;
        }else if(msg.equals("<")){
            return  10;
        }else if(msg.equals(">")){
            return  11;
        }else if(msg.equals("|")){
            return  12;
        }else if(msg.equals(",")){
            return  13;
        }else if(msg.equals("(")){
            return  14;
        }else if(msg.equals(")")){
            return  15;
        }else if(msg.equals("{")){
            return  16;
        }else if(msg.equals("}")){
            return  17;
        }else if(msg.equals("[")){
            return  18;
        }else if(msg.equals("]")){
            return  19;
        }else if (msg.equals(" ")){
            return 20;
        }else {
            return 0;
        }
    }
    private boolean isdigit (String msg){
        try {
            Integer.parseInt(msg);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
