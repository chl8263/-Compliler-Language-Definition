public class Model {
    // letter(0) , digit(1) , +(2) , -(3) , *(4) , /(5) , %(6) , &(7) , !(8) ,=(9) , <(10) , >(11) , |(12)  , ','(13) , ((14) , )(15) , {(16) , }(17) , [(18) , ](19) , "\"" , "'"
    private String name;    //keyword (25) , special_symbol  , identifier (26) , number (27) , string (28) , char (29)
    private int t_number;
    private String t_value = "0";

    public Model(String name, int t_number, String t_value) {
        this.name = name;
        this.t_number = t_number;
        this.t_value = t_value;
    }
    public Model(String name, int t_number) {
        this.name = name;
        this.t_number = t_number;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getT_number() {
        return t_number;
    }

    public void setT_number(int t_number) {
        this.t_number = t_number;
    }

    public String getT_value() {
        return t_value;
    }

    public void setT_value(String t_value) {
        this.t_value = t_value;
    }
}
