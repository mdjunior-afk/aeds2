
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class G {
    int id;
    String name;
    String releaseDate;
    String estimatedOwners;
    float price;
    String[] supportedLanguages;
    int metacriticScore;
    

    G next;


}

public class Game {
    public static void main(String[] args) {
        readCSV("games.csv");
    }

    public static String replaceMonth(String pre_month) {
        String converted_month = "";

        if (pre_month.charAt(0) == 'J' && pre_month.charAt(1) == 'a' && pre_month.charAt(2) == 'n') {
            converted_month += "01";
        } else if (pre_month.charAt(0) == 'F' && pre_month.charAt(1) == 'e' && pre_month.charAt(2) == 'b') {
            converted_month += "02";
        } else if (pre_month.charAt(0) == 'M' && pre_month.charAt(1) == 'a' && pre_month.charAt(2) == 'r') {
            converted_month += "03";
        } else if (pre_month.charAt(0) == 'A' && pre_month.charAt(1) == 'p' && pre_month.charAt(2) == 'r') {
            converted_month += "04";
        } else if (pre_month.charAt(0) == 'M' && pre_month.charAt(1) == 'a' && pre_month.charAt(2) == 'y') {
            converted_month += "05";
        } else if (pre_month.charAt(0) == 'J' && pre_month.charAt(1) == 'u' && pre_month.charAt(2) == 'n') {
            converted_month += "06";
        } else if (pre_month.charAt(0) == 'J' && pre_month.charAt(1) == 'u' && pre_month.charAt(2) == 'l') {
            converted_month += "07";
        } else if (pre_month.charAt(0) == 'A' && pre_month.charAt(1) == 'u' && pre_month.charAt(2) == 'g') {
            converted_month += "08";    
        } else if (pre_month.charAt(0) == 'S' && pre_month.charAt(1) == 'e' && pre_month.charAt(2) == 'p') {
            converted_month += "09";
        } else if (pre_month.charAt(0) == 'O' && pre_month.charAt(1) == 'c' && pre_month.charAt(2) == 't') {
            converted_month += "10";
        } else if (pre_month.charAt(0) == 'N' && pre_month.charAt(1) == 'o' && pre_month.charAt(2) == 'v') {
            converted_month += "11";
        } else if (pre_month.charAt(0) == 'D' && pre_month.charAt(1) == 'e' && pre_month.charAt(2) == 'c') {
            converted_month += "12";
        } else {
            converted_month += "01";
        }

        return converted_month;
    }

    public static void readCSV(String file) {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            boolean first_line = true;
            boolean get_date = false;

            while ((line = br.readLine()) != null) {

                StringBuilder sb = new StringBuilder();
                boolean is_list = false;
                String id = "";
                int count = 0;

                boolean h = false;

                if (!first_line) {
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) != ',') {
                            if (line.charAt(i) == '"') {
                                if (h) {
                                    sb.append(']');
                                    h = false;
                                    is_list = false;
                                }
                                continue;
                            } else if (line.charAt(i) == '\'') {
                                continue;
                            }

                            if (line.charAt(i) == '[') {
                                is_list = true;
                            } else if (line.charAt(i) == ']') {
                                is_list = false;
                            }

                            if (count == 10 && !h) {
                                sb.append('[');
                                h = true;
                            } else if (count == 11 && !h) {
                                sb.append('[');
                                h = true;
                            } else if (count == 12 && !h) {
                                sb.append('[');
                                h = true;
                                is_list = true;
                            } else if (count == 13 && !h) {
                                sb.append('[');
                                h = true;
                                is_list = true;
                            } else if (count == 14 && !h) {
                                sb.append('[');
                                h = true;
                                is_list = true;
                            }

                            sb.append(line.charAt(i));

                            if (count == 0) {
                                id += line.charAt(i);
                            }
                            
                            if (count == 2 && !get_date) {
                                String day = "";
                                String pre_month = "";
                                String year = "";
                                while (line.charAt(i) != ' ') {
                                    pre_month += line.charAt(i);
                                    i++;
                                }

                                // Fazer tratamento dos meses
                                String month = replaceMonth(pre_month);
                                i++;

                                while (line.charAt(i + 1) != ',') {
                                    day += line.charAt(i + 1);
                                    i++;
                                }

                                i++;
                                i++;

                                while (line.charAt(i + 1) != ',') {
                                    if (line.charAt(i + 1) != '"') {
                                        year += line.charAt(i + 1);
                                    }

                                    i++;
                                }

                                get_date = true;
                                
                                count++;
                                String completed_date = day + "/" + month + "/" + year;
                                sb.append(completed_date);
                            }
                        
                        } else {
                            if (is_list && count == 13) {
                                if (line.charAt(i) == ' ') {
                                    sb.append('"');
                                }
                            }

                            if (!is_list) {
                                if (h) {
                                    sb.append(']');
                                    h = false;
                                }

                                sb.append(" ## ");
                                count++;
                            } else {
                                if (h) {
                                    sb.append(", ");
                                } else {
                                    sb.append(",");
                                }
                            }
                        }
                    }
                    if (id.equals("866800")) {
                        System.out.println(line);
                        System.out.println(sb.toString());
                    }
                } else {
                    first_line = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


