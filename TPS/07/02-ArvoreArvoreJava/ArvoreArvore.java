import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ArvoreArvore
{
    public static void main(String[] args) 
    {
        ArvoreArvoreModel tree = new ArvoreArvoreModel();
        GameManager gameManager = new GameManager();
        Scanner scan = new Scanner(System.in);


        String path = "/tmp/games.csv";

        long start = System.nanoTime();
        
        while (true)
        {
            String gameID = scan.nextLine();

            if (equals(gameID, "FIM")) break;

            Game game = gameManager.findGame(path, gameID, "id");

            tree.root = tree.insert(game.name, game.estimatedOwners % 15, tree.root);
        }

        while (true)
        {
            String gameName = scan.nextLine();

            if (equals(gameName, "FIM")) break;

            Game game = gameManager.findGame(path, gameName, "name");

            StringBuilder sb = new StringBuilder();
            sb.append("raiz ");
            Node index = tree.search(tree.root, game.estimatedOwners % 15, sb);
            
            if (index != null) {
                StringBuilder sbSubTree = new StringBuilder();
                tree.searchSubTree(gameName, index.root, sbSubTree);
                System.out.print("=> " + gameName + " => " + sb.toString());
                System.out.println(sbSubTree.toString());
            }
        }

        long end = System.nanoTime();
        double tempoExecucao = (end - start) / 1_000_000.0;

        String matricula = "896612";
        String linha = tree.numCompare + "\t" + tempoExecucao;

        try (FileWriter writer = new FileWriter(matricula + "_arvoredearvore.txt")) {
            writer.write(linha);
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo de log: " + e.getMessage());
        }
    }

    public static boolean equals(String s1, String s2)
    {
        if (s1.length() != s2.length()) return false;

        int i = 0;

        while (i < s1.length())
        {
            if (s1.charAt(i) != s2.charAt(i)) return false;
            i++;
        }

        return true;
    }
}

class ArvoreArvoreModel
{
    Node root;
    int numCompare = 0;

    public ArvoreArvoreModel()
    {
        int[] nums = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};

        for (int i: nums)
            root = createTree(i, root);
    }

    private Node createTree(int value, Node root)
    {
        if (root == null) return new Node(value);

        if (value < root.value)
        {
            root.left = createTree(value, root.left);
        }
        else if (value > root.value)
        {
            root.right = createTree(value, root.right);
        }

        return root;
    }

    public Node insert(String value, int index, Node node)
    {
        if (index == node.value)
        {
            numCompare++;
            node.root = insertSubTree(value, node.root);

            return node;
        }

        if (index < node.value)
        {
            numCompare++;
            node.left =  insert(value, index, node.left);
        }
        else if (index > node.value)
        {
            numCompare++;
            node.right =  insert(value, index, node.right);
        }

        return node;
    }

    public Node2 insertSubTree(String value, Node2 node)
    {
        if (node == null)
        {
            return new Node2(value);
        }

        numCompare++;
        int compareStrings = compareStrings(value, node.name);

        if (compareStrings == -1)
        {
            numCompare++;
            node.left = insertSubTree(value, node.left);
        }
        else if (compareStrings == 1)
        {
            numCompare++;
            node.right = insertSubTree(value, node.right);
        }

        return node;
    }

    public Node search(Node node, int index, StringBuilder s)
    {
        if (node == null)
            return null;

        if (index == node.value) {
            return node;
        }

        if (index < node.value) {
            s.append(" ESQ ");
            return search(node.left, index, s);
        } else {
            s.append(" DIR ");
            return search(node.right, index, s);
        }
    }


    public boolean searchSubTree(String value, Node2 node, StringBuilder s)
    {
        if (node == null) 
        {
            s.append(" NAO");
            return false;
        }

        if (value.equals(node.name)) {
            s.append(" SIM");
            return true;
        }

        if (value.compareTo(node.name) < 0) {
            s.append("esq ");
            return searchSubTree(value, node.left, s);
        }
        else {
            s.append("dir ");
            return searchSubTree(value, node.right, s);
        }
    }

    private int compareStrings(String s1, String s2) 
    {
        int minLength = Math.min(s1.length(), s2.length());

        for (int i = 0; i < minLength; i++) 
        {
            if (s1.charAt(i) < s2.charAt(i))
                return -1; // antes
            if (s1.charAt(i) > s2.charAt(i))
                return 1; // depois
        }

        if (s1.length() < s2.length())
            return -1; // antes
        if (s1.length() > s2.length())
            return 1; // depois
        
        return 0; // iguais
    }
}

class Node
{
    int value;

    Node left, right;

    Node2 root;

    public Node(int value)
    {
        this.value = value;
        this.left = this.right = null;
    }
}

class Node2
{
    String name;

    Node2 left, right;

    public Node2(String name)
    {
        this.name = name;
        this.left = this.right = null;
    }
}

class GameManager
{
  public Game findGame(String filename, String search, String key) {
    String line;

    boolean firstLine = true;

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      while ((line = br.readLine()) != null) {
        if (firstLine) {
          firstLine = false;
          continue;
        }

        // Contabiliza qual virgula está para separar os atributos
        int commaCount = 0;

        // Atributos do game
        Data idData = new Data();
        Data nameData = new Data();
        Data releasedData = new Data();
        Data estimatedOwners = new Data();
        Data priceData = new Data();
        Data supportedLanguagesData = new Data();
        Data metacriticScoreData = new Data();
        Data userScoreData = new Data();
        Data achievementsData = new Data();
        Data publishersData = new Data();
        Data developersData = new Data();
        Data categoriesData = new Data();
        Data genresData = new Data();
        Data tagsData = new Data();

        boolean found = true;

        // Passa por cada caractere
        for (int i = 0; i < line.length(); i++) {
          if (line.charAt(i) == ',') {
            commaCount++;
          }

          if (!found)
            break;

          switch (commaCount) {
            // GameID
            case 0:
              idData = getGameID(line, i);
              if (idData.data.equals("22200")) {
                line = line.replace(
                    "['English[b][/b]', 'German', 'French', 'Polish', 'Spanish - Spain', 'Italian \\r\\n\\r\\n[b][/b] ']",
                    "['English', 'German', 'French', 'Polish', 'Spanish - Spain', 'Italian']");
              } else if (idData.data.equals("344500")) {
                line = line.replace("[bracket]games", "bracket games");
              }

              if (key.equals("id") && !search.equals(idData.data)) {
                found = false;
                break;
              }

              i = idData.pos;
              commaCount++;
              break;
            // GameName
            case 1:
              nameData = getGameName(line, i);
              
              if (key.equals("name") && !search.equals(nameData.data)) {
                found = false;
                break;
              }

              i = nameData.pos;
              commaCount++;
              break;
            case 2:
              releasedData = getGameDate(line, i);
              i = releasedData.pos;
              commaCount++;
            case 3:
              estimatedOwners = getGameOwners(line, i);
              i = estimatedOwners.pos;
              commaCount++;
            case 4:
              priceData = getGamePrice(line, i);
              i = priceData.pos;
              commaCount++;
            case 5:
              supportedLanguagesData = getGameLanguages(line, i);
              i = supportedLanguagesData.pos;
              commaCount++;
            case 6:
              metacriticScoreData = getGameMetacriticScore(line, i);
              i = metacriticScoreData.pos;
              commaCount++;
            case 7:
              userScoreData = getGameUserScore(line, i);
              i = userScoreData.pos;
              commaCount++;
            case 8:
              achievementsData = getGameAchievements(line, i);
              i = achievementsData.pos;
              commaCount++;
            case 9:
              publishersData = getGamePublishers(line, i);
              i = publishersData.pos;
              commaCount++;
            case 10:
              developersData = getGameDevelopers(line, i);
              i = developersData.pos;
              commaCount++;
            case 11:
              categoriesData = getGameCategories(line, i);
              i = categoriesData.pos;
              commaCount++;
            case 12:
              genresData = getGameGenres(line, i);
              i = genresData.pos;
              commaCount++;
            case 13:
              if (i + 1 < line.length()) {
                tagsData = getGameTags(line, i);
                i = tagsData.pos;
                commaCount++;
              }
            default:
              break;
          }
        }

        if (!found) {
          continue;
        }

        // System.out.println(line);

        Game newGame = new Game(Integer.parseInt(idData.data), nameData.data,
            releasedData.data, Integer.parseInt(estimatedOwners.data), Float.parseFloat(priceData.data),
            convertStrings(supportedLanguagesData.data),
            Integer.parseInt(metacriticScoreData.data), Float.parseFloat(userScoreData.data),
            Integer.parseInt(achievementsData.data),
            convertStrings(publishersData.data), convertStrings(developersData.data),
            convertStrings(categoriesData.data), convertStrings(genresData.data),
            convertStrings(tagsData.data));

        return newGame;

      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public String[] convertStrings(String data) {
    int count = 0;

    if (data.length() > 2) {
      if (data.charAt(0) == '[' && data.charAt(1) == '[') {
        data = data.replace("[[", "[").replace("]]", "]");
      }
    }

    for (int i = 0; i < data.length(); i++) {
      if (data.charAt(i) == ',') {
        count++;
      }
    }

    String[] arr = new String[count + 1];

    String value = "";
    int index = 0;

    for (int i = 1; i < data.length(); i++) {
      char c = data.charAt(i);

      if (c == ',' || c == ']') {
        arr[index] = value;
        value = "";
        index++;
        continue;
      }

      value += c;
    }

    return arr;
  }

  public Data getGameID(String line, int pos) {
    String id = "";

    while (line.charAt(pos) != ',') {
      id += line.charAt(pos);
      pos++;
    }

    return new Data(pos, id);
  }

  public Data getGameName(String line, int pos) {
    String name = "";

    if (line.charAt(pos) == '"') {
      pos++;
      while (line.charAt(pos) != '"') {
        name += line.charAt(pos);
        pos++;
      }
      pos++;
    } else {
      while (line.charAt(pos) != ',') {
        name += line.charAt(pos);
        pos++;
      }
    }

    return new Data(pos, name);
  }

  public Data getGameDate(String line, int pos) {
    String date = "";

    if (line.charAt(pos) == '"') {
      pos++;
      while (line.charAt(pos) != '"') {
        date += line.charAt(pos);
        pos++;
      }
      pos++;
    } else {
      while (line.charAt(pos) != ',') {
        date += line.charAt(pos);
        pos++;
      }
    }

    pos++;

    // Converte a data para dd/mm/YYYY
    String formatedDate = "";
    String preMonth = "";
    String convertedMonth = "";
    String day = "";
    String year = "";

    int i = 0;

    while (date.charAt(i) != ' ') {
      preMonth += date.charAt(i);
      i++;
    }

    if (preMonth.charAt(0) == 'J' && preMonth.charAt(1) == 'a' && preMonth.charAt(2) == 'n') {
      convertedMonth += "01";
    } else if (preMonth.charAt(0) == 'F' && preMonth.charAt(1) == 'e' && preMonth.charAt(2) == 'b') {
      convertedMonth += "02";
    } else if (preMonth.charAt(0) == 'M' && preMonth.charAt(1) == 'a' && preMonth.charAt(2) == 'r') {
      convertedMonth += "03";
    } else if (preMonth.charAt(0) == 'A' && preMonth.charAt(1) == 'p' && preMonth.charAt(2) == 'r') {
      convertedMonth += "04";
    } else if (preMonth.charAt(0) == 'M' && preMonth.charAt(1) == 'a' && preMonth.charAt(2) == 'y') {
      convertedMonth += "05";
    } else if (preMonth.charAt(0) == 'J' && preMonth.charAt(1) == 'u' && preMonth.charAt(2) == 'n') {
      convertedMonth += "06";
    } else if (preMonth.charAt(0) == 'J' && preMonth.charAt(1) == 'u' && preMonth.charAt(2) == 'l') {
      convertedMonth += "07";
    } else if (preMonth.charAt(0) == 'A' && preMonth.charAt(1) == 'u' && preMonth.charAt(2) == 'g') {
      convertedMonth += "08";
    } else if (preMonth.charAt(0) == 'S' && preMonth.charAt(1) == 'e' && preMonth.charAt(2) == 'p') {
      convertedMonth += "09";
    } else if (preMonth.charAt(0) == 'O' && preMonth.charAt(1) == 'c' && preMonth.charAt(2) == 't') {
      convertedMonth += "10";
    } else if (preMonth.charAt(0) == 'N' && preMonth.charAt(1) == 'o' && preMonth.charAt(2) == 'v') {
      convertedMonth += "11";
    } else if (preMonth.charAt(0) == 'D' && preMonth.charAt(1) == 'e' && preMonth.charAt(2) == 'c') {
      convertedMonth += "12";
    } else {
      convertedMonth += "01";
    }

    if (date.charAt(i + 2) == ',' || date.charAt(i + 3) == ',') {
      while (date.charAt(i + 1) != ',') {
        day += date.charAt(i + 1);
        i++;
      }

      if (day.length() == 1) {
        day = "0" + day;
      }

      for (int j = 0; j < 4; j++, i++) {
        year += date.charAt(i + 3);
      }
    } else {
      day = "01";

      for (int j = 0; j < 4; j++, i++) {
        year += date.charAt(i + 1);
      }
    }

    formatedDate += day + "/" + convertedMonth + "/" + year;

    return new Data(pos, formatedDate);
  }

  public Data getGameOwners(String line, int pos) {
    String owners = "";

    while (line.charAt(pos) != ',') {
      owners += line.charAt(pos);
      pos++;
    }

    pos++;

    return new Data(pos, owners);
  }

  public Data getGamePrice(String line, int pos) {
    String price = "";

    while (line.charAt(pos) != ',') {
      price += line.charAt(pos);
      pos++;
    }

    pos++;

    return new Data(pos, price);
  }

  public Data getGameLanguages(String line, int pos) {
    String languages = "[";

    if (line.charAt(pos) == '"') {
      pos++;
      if (line.charAt(pos) == '[') {
        pos++;

        while (line.charAt(pos) != ']') {
          if (line.charAt(pos) == '\'') {
            pos++;
            continue;
          }

          languages += line.charAt(pos);
          pos++;
        }
        pos++;
      } else {
        while (line.charAt(pos) != '"') {
          if (line.charAt(pos) == ',') {
            languages += ", ";
          } else {
            languages += line.charAt(pos);
          }

          pos++;
        }
      }
    } else {
      if (line.charAt(pos) == '[') {
        pos++;

        while (line.charAt(pos) != ']') {
          if (line.charAt(pos) == '\'') {
            pos++;
            continue;
          }

          languages += line.charAt(pos);
          pos++;
        }
        pos++;
      }
    }

    languages += "]";

    if (line.charAt(pos) == '"') {
      pos++;
      pos++;
    } else {
      pos++;
    }

    return new Data(pos, languages);
  }

  public Data getGameMetacriticScore(String line, int pos) {
    String metacriticScore = "";

    while (line.charAt(pos) != ',') {
      metacriticScore += line.charAt(pos);
      pos++;
    }

    pos++;

    return new Data(pos, metacriticScore);
  }

  public Data getGameUserScore(String line, int pos) {
    String userScore = "";

    while (line.charAt(pos) != ',') {
      userScore += line.charAt(pos);
      pos++;
    }

    pos++;

    return new Data(pos, userScore);
  }

  public Data getGameAchievements(String line, int pos) {
    String achievements = "";

    while (line.charAt(pos) != ',') {
      achievements += line.charAt(pos);
      pos++;
    }

    pos++;

    return new Data(pos, achievements);
  }

  public Data getGamePublishers(String line, int pos) {
    String publishers = "[";

    if (line.charAt(pos) == '"') {
      pos++;
      if (line.charAt(pos) == '[') {
        pos++;

        while (line.charAt(pos) != ']') {
          if (line.charAt(pos) == '\'') {
            pos++;
            continue;
          }

          publishers += line.charAt(pos);
          pos++;
        }
        pos++;
      } else {
        while (line.charAt(pos) != '"') {
          if (line.charAt(pos) == ',' && line.charAt(pos + 1) != ' ') {
            publishers += ", ";
          } else {
            publishers += line.charAt(pos);
          }

          pos++;
        }
        pos++;
      }
    } else {
      while (line.charAt(pos) != ',') {
        publishers += line.charAt(pos);
        pos++;
      }
    }

    publishers += "]";

    pos++;

    return new Data(pos, publishers);
  }

  public Data getGameDevelopers(String line, int pos) {
    String developers = "[";

    if (line.charAt(pos) == ']' && line.charAt(pos + 1) == '[') {
      pos += 3;
    }

    if (line.charAt(pos) == '"') {
      pos++;
      if (line.charAt(pos) == '[') {
        pos++;

        while (line.charAt(pos) != ']') {
          if (line.charAt(pos) == '\'') {
            pos++;
            continue;
          }

          developers += line.charAt(pos);
          pos++;
        }
        pos++;
      } else {
        while (line.charAt(pos) != '"') {
          if (line.charAt(pos) == ',' && line.charAt(pos + 1) != ' ') {
            developers += ", ";
          } else {
            developers += line.charAt(pos);
          }

          pos++;
        }
      }
    } else {
      while (line.charAt(pos) != ',') {
        developers += line.charAt(pos);
        pos++;
      }
    }

    developers += "]";

    pos++;

    return new Data(pos, developers);
  }

  public Data getGameCategories(String line, int pos) {
    String categories = "[";

    if (line.charAt(pos) == ',') {
      pos++;
    }

    if (line.charAt(pos) == '"') {
      pos++;
      if (line.charAt(pos) == '[') {
        pos++;

        while (line.charAt(pos) != ']') {
          if (line.charAt(pos) == '\'') {
            pos++;
            continue;
          }

          categories += line.charAt(pos);
          pos++;
        }
        pos++;
      } else {
        while (line.charAt(pos) != '"') {
          if (line.charAt(pos) == ',') {
            categories += ", ";
          } else {
            categories += line.charAt(pos);
          }

          pos++;
        }
      }
    } else {
      while (line.charAt(pos) != ',') {
        categories += line.charAt(pos);
        pos++;
      }
      pos--;
    }

    categories += "]";

    pos++;
    pos++;

    return new Data(pos, categories);
  }

  public Data getGameGenres(String line, int pos) {
    String genres = "[";

    if (line.charAt(pos) == '"') {
      pos++;
      if (line.charAt(pos) == '[') {
        pos++;

        while (line.charAt(pos) != ']') {
          if (line.charAt(pos) == '\'') {
            pos++;
            continue;
          }

          genres += line.charAt(pos);
          pos++;
        }
        pos++;
      } else {
        while (line.charAt(pos) != '"') {
          if (line.charAt(pos) == ',') {
            genres += ", ";
          } else {
            genres += line.charAt(pos);
          }

          pos++;
        }
      }
    } else {
      while (line.charAt(pos) != ',') {
        genres += line.charAt(pos);
        pos++;
      }
      pos--;
    }

    genres += "]";

    pos++;
    if (pos + 1 < line.length()) {
      pos++;
    }

    return new Data(pos, genres);
  }

  public Data getGameTags(String line, int pos) {
    String tags = "[";

    if (line.charAt(pos) == '"') {
      pos++;
      if (line.charAt(pos) == '[') {
        pos++;

        while (line.charAt(pos) != ']') {
          if (line.charAt(pos) == '\'') {
            pos++;
            continue;
          }

          tags += line.charAt(pos);
          pos++;
        }
        pos++;
      } else {
        while (line.charAt(pos) != '"') {
          if (line.charAt(pos) == ',') {
            tags += ", ";
          } else {
            tags += line.charAt(pos);
          }

          pos++;
        }
      }
    }

    tags += "]";

    pos++;
    pos++;

    return new Data(pos, tags);
  }
}

class Data {
  int pos;
  String data;

  public Data() {
    this.pos = 0;
    this.data = "";
  }

  public Data(int pos, String data) {
    this.pos = pos;
    this.data = data;
  }
}

class Game {
  int id;
  String name, releaseDate;
  int estimatedOwners;
  int metacriticScore;
  int achievements;
  float price;
  float userScore;
  String[] supportedLanguages, publishers, developers, categories, genres, tags;

  public Game() {
    this.id = 0;
    this.name = "";
    this.releaseDate = "";
    this.estimatedOwners = 0;
    this.metacriticScore = 0;
    this.achievements = 0;
    this.price = 0;
    this.userScore = 0;
    this.supportedLanguages = new String[0];
    this.publishers = new String[0];
    this.developers = new String[0];
    this.categories = new String[0];
    this.genres = new String[0];
    this.tags = new String[0];
  }

  public Game(int id, String name, String releaseDate, int estimatedOwners,
      float price, String[] supportedLanguages, int metacriticScore,
      float userScore, int achievements, String[] publishers, String[] developers,
      String[] categories, String[] genres, String[] tags) {
    this.id = id;
    this.name = name;
    this.releaseDate = releaseDate;
    this.estimatedOwners = estimatedOwners;
    this.price = price;
    this.supportedLanguages = supportedLanguages;
    this.metacriticScore = metacriticScore;
    this.userScore = userScore;
    this.achievements = achievements;
    this.publishers = publishers;
    this.developers = developers;
    this.categories = categories;
    this.genres = genres;
    this.tags = tags;
  }

  public void showInfo() {
    System.out.print("=> " + this.id + " ## ");
    System.out.print(this.name + " ## ");
    System.out.print(this.releaseDate + " ## ");
    System.out.print(this.estimatedOwners + " ## ");
    System.out.print(price + " ## ");

    System.out.print("[");
    for (int i = 0; i < this.supportedLanguages.length; i++) {
      if (i != this.supportedLanguages.length - 1) {
        System.out.print(supportedLanguages[i] + ",");
      } else {
        System.out.print(supportedLanguages[i] + "] ## ");
      }
    }

    System.out.print(this.metacriticScore + " ## ");
    System.out.print(this.userScore + " ## ");
    System.out.print(this.achievements + " ## ");

    System.out.print("[");
    for (int i = 0; i < this.publishers.length; i++) {
      if (i != this.publishers.length - 1) {
        System.out.print(publishers[i] + ",");
      } else {
        System.out.print(publishers[i] + "] ## ");
      }
    }

    System.out.print("[");
    for (int i = 0; i < this.developers.length; i++) {
      if (i != this.developers.length - 1) {
        System.out.print(developers[i] + ",");
      } else {
        System.out.print(developers[i] + "] ## ");
      }
    }

    System.out.print("[");
    for (int i = 0; i < this.categories.length; i++) {
      if (i != this.categories.length - 1) {
        System.out.print(categories[i] + ",");
      } else {
        System.out.print(categories[i] + "] ## ");
      }
    }

    System.out.print("[");
    for (int i = 0; i < this.genres.length; i++) {
      if (i != this.genres.length - 1) {
        System.out.print(genres[i] + ",");
      } else {
        System.out.print(genres[i] + "] ## ");
      }
    }

    System.out.print("[");
    for (int i = 0; i < this.tags.length; i++) {
      if (i != this.tags.length - 1) {
        System.out.print(tags[i] + ",");
      } else {
        System.out.print(tags[i] + "] ##");
      }
    }

    System.out.println();

  }

}