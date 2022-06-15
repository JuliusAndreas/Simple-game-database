package database.connectivity;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julius Andreas
 */
public class DatabaseConnectivity {

    public static void main(String[] args) {
        
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\FAAL\\Games.db");
        } catch (Exception e) {
            System.err.println("Problem Detected");
        }
        System.out.println("Database Opened Successfully");
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnectivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Welcome to Games Database Application");
        Scanner scanner = new Scanner(System.in);
        System.out.println("To view top 10 best games, type 1");
        System.out.println("To view top 10 worst games, type 2");
        System.out.println("To view games with same year or month, type 3");
        System.out.println("To view the platform with most games, type 4");
        System.out.println("To view the os with most games, type 5");
        System.out.println("To view the characters of the best game, type 6");
        System.out.println("To view the top 10 best genres, type 7");
        System.out.println("To view the companies which produced best games, type 8");
        System.out.println("To view the games with most memories, type 9");
        System.out.println("To view the genres with most memories, type 10");
        System.out.println("To view the longest serie of games, type 11");
        System.out.println("To view the top 10 games with most NPCs, type 12");
        System.out.println("To view all games, type 13");
        System.out.println("To view all characters, type 14");
        System.out.println("To view all companies, type 15");
        System.out.println("To view all memories, type 16");
        System.out.println("To view all genres, type 17");
        System.out.println("To insert data into Game, type 18");
        System.out.println("To insert data into Character, type 19");
        System.out.println("To insert data into Company, type 20");
        System.out.println("To insert data into Memory, type 21");
        System.out.println("To insert data into OS, type 22");
        System.out.println("To insert data into Platform, type 23");
        System.out.println("To insert data into Genre, type 24");
        System.out.println("To insert data into SequelOf, type 25");
        System.out.println("To insert data into Produce, type 26");
        System.out.println("To insert data into GameGenre, type 27");
        System.out.println("To insert data into GameCharacter, type 28");
        System.out.println("To insert data into GameOS, type 29");
        System.out.println("To insert data into GamePlatform, type 30");
        System.out.println("To simply exit the program, type -1");
        int chosen = scanner.nextInt();
        while (chosen != -1) {
            Connection c = null;
            try {
                c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\FAAL\\Games.db");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.exit(0);
            }
            String sql;
            switch (chosen) {
                case 1:
                    sql = "select * from Game Order By Score DESC LIMIT 10;";
                    queryExecuter(sql, TableType.Game, c);
                    break;
                case 2:
                    sql = "select * from Game Order By Score ASC LIMIT 10;";
                    queryExecuter(sql, TableType.Game, c);
                    break;
                case 3:
                    System.out.println("Type 1 for same year and type 2 for same month:");
                    int number = scanner.nextInt();
                    switch (number) {
                        case 1:
                            System.out.println("Enter the Year:");
                            int year = scanner.nextInt();
                            try {
                                Statement stmt = c.createStatement();
                                ResultSet rs = stmt.executeQuery(
                                        "select * from Game WHERE finishedDate LIKE '%" + year + "%';");
                                while (rs.next()) {
                                    String name = rs.getString("Title");
                                    int id = rs.getInt("GameID");
                                    String release = rs.getString("ReleaseDate");
                                    String finished = rs.getString("FinishedDate");
                                    int serieLength = rs.getInt("SerieLength");
                                    int score = rs.getInt("Score");
                                    String pov = rs.getString("POV");
                                    boolean mc = rs.getBoolean("MultiplayerCapability");
                                    System.out.println(name + " , " + id + " , " + release + " , "
                                            + finished + " , " + serieLength
                                            + " , " + score + " , " + pov + " Has Multiplayer?: " + mc);
                                }
                                rs.close();
                                stmt.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.exit(0);
                            }
                            break;
                        case 2:
                            System.out.println("Enter the Month:");
                            int month = scanner.nextInt();
                            try {
                                Statement stmt = c.createStatement();
                                ResultSet rs;
                                if (month < 10) {
                                    rs = stmt.executeQuery(
                                            "select * from Game WHERE finishedDate LIKE " + "'%-0" + month + "%';");
                                } else {
                                    rs = stmt.executeQuery(
                                            "select * from Game WHERE finishedDate LIKE '%" + month + "%';");
                                }
                                while (rs.next()) {
                                    String name = rs.getString("Title");
                                    int id = rs.getInt("GameID");
                                    String release = rs.getString("ReleaseDate");
                                    String finished = rs.getString("FinishedDate");
                                    int serieLength = rs.getInt("SerieLength");
                                    int score = rs.getInt("Score");
                                    String pov = rs.getString("POV");
                                    boolean mc = rs.getBoolean("MultiplayerCapability");
                                    System.out.println(name + " , " + id + " , " + release + " , "
                                            + finished + " , " + serieLength
                                            + " , " + score + " , " + pov + " Has Multiplayer?: " + mc);
                                }
                                rs.close();
                                stmt.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                System.exit(0);
                            }
                            break;
                        default:
                            System.err.println("You did not type the right number.");
                            System.exit(0);
                    }
                    break;
                case 4:
                    sql = "select Name,PlatformID,COUNT(*) as count from Platform,GamePlatform Group By PlatformID,PlatformKey Order By count DESC Limit 1;";
                    queryExecuter(sql, TableType.Platform, c);
                    break;
                case 5:
                    sql = "select Name,OSID,COUNT(*) as count from OS,GameOS Group By OSID,OSKey "
                            + "Order By count DESC Limit 1;";
                    queryExecuter(sql, TableType.OS, c);
                    break;
                case 6:
                    sql = "select Name,CharacterID,Age,Playability from Character,GameCharacter WHERE CharacterID = "
                            + "CharacterKey AND GameKey = (select GameID from Game Order By Score DESC" + " LIMIT 1);";
                    queryExecuter(sql, TableType.Character, c);
                    break;
                case 7:
                    sql = "select * from Genre Order By Score DESC LIMIT 10;";
                    queryExecuter(sql, TableType.Genre, c);
                    break;
                case 8:
                    sql = "select CompanyID,Name,StockPrice,Country,NumberOfGamesMade,FoundationDate from Company INNER join Produce ON CompanyID = "
                            + "CompanyKey INNER join Game ON GameID = GameKey Order by score DESC LIMIT "
                            + "10;";
                    queryExecuter(sql, TableType.Company, c);
                    break;
                case 9:
                    sql = "select DISTINCT GameKey,Title,Count(Description) as count,ReleaseDate,GameID,Title,FinishedDate,SerieLength,POV,Score,MultiplayerCapability from Game INNER "
                            + "JOIN Memory ON GameID = GameKey Group By GameID Order By count DESC "
                            + "LIMIT 10;";
                    queryExecuter(sql, TableType.Game, c);
                    break;
                case 10:
                    sql = "select Title,GenreID,Count(*) as count,Score from Genre INNER JOIN GameGenre ON "
                            + "GenreID = GenreKey INNER JOIN Memory ON Memory.GameKey = "
                            + "GameGenre.GameKey Group By GenreID Order By count DESC LIMIT 10;";
                    queryExecuter(sql, TableType.Genre, c);
                    break;
                case 11:
                    sql = "select * from Game Order By serieLength DESC LIMIT 10;";
                    queryExecuter(sql, TableType.Game, c);
                    break;
                case 12:
                    sql = "select ReleaseDate,FinishedDate,SerieLength,POV,Score,MultiplayerCapability,GameID,Title, count(*) as count from Game INNER Join GameCharacter ON "
                            + "GameID = GameKey Inner join Character On CharacterKey = CharacterID AND "
                            + "Playability = FALSE Group by GameID Order by count DESC LIMIT 10;";
                    queryExecuter(sql, TableType.Game, c);
                    break;
                case 13:
                    sql = "select * from Game;";
                    queryExecuter(sql, TableType.Game, c);
                    break;
                case 14:
                    sql = "select * from Character;";
                    queryExecuter(sql, TableType.Character, c);
                    break;
                case 15:
                    sql = "select * from Company;";
                    queryExecuter(sql, TableType.Company, c);
                    break;
                case 16:
                    sql = "select * from Memory;";
                    queryExecuter(sql, TableType.Memory, c);
                    break;
                case 17:
                    sql = "select * from Genre;";
                    queryExecuter(sql, TableType.Genre, c);
                    break;
                case 18:
                    System.out.println("Insert the id of the game you want to insert:");
                    String stringId = scanner.nextLine();
                    stringId = scanner.nextLine();
                    if (stringId.equals("")) {
                        stringId = "null";
                    }
                    System.out.println("Insert the name of the game you want to insert:");
                    String name = scanner.nextLine();
                    if (name.equals("")) {
                        name = "null";
                    }
                    System.out.println("Insert the release date of the game you want to insert:");
                    String releaseDate = scanner.nextLine();
                    if (releaseDate.equals("")) {
                        releaseDate = "null";
                    }
                    System.out.println("Insert the finished date of the game you want to insert:");
                    String finishedDate = scanner.nextLine();
                    if (finishedDate.equals("")) {
                        finishedDate = "null";
                    }
                    System.out.println("Insert the Serie length of the game you want to insert:");
                    String serieLengthStr = scanner.nextLine();
                    if (serieLengthStr.equals("")) {
                        serieLengthStr = "null";
                    }
                    System.out.println("Insert the Score of the game you want to insert:");
                    String scoreStr = scanner.nextLine();
                    if (scoreStr.equals("")) {
                        scoreStr = "null";
                    }
                    System.out.println("Insert the POV of the game you want to insert:");
                    String pov = scanner.nextLine();
                    if (pov.equals("")) {
                        pov = "null";
                    }
                    System.out.println("type true if your game has multiplayer capability, else type false:");
                    String mc;
                    while (true) {
                        mc = scanner.nextLine();
                        if (mc.equalsIgnoreCase("true")) {
                            break;
                        } else if (mc.equalsIgnoreCase("false")) {
                            break;
                        } else if (mc.equals("")) {
                            mc = "null";
                            break;
                        } else {
                            System.out.println("Type true or false please");
                        }
                    }
                    sql = "INSERT INTO Game"
                            + "(GameID, Title, ReleaseDate, FinishedDate, Score, POV, MultiplayerCapability, SerieLength )"
                            + " values(" + stringId + ",'" + name + "',\"" + releaseDate + "\",\"" + finishedDate + "\"," + scoreStr + ", '" + pov + "', " + mc + "," + serieLengthStr + ");";
                    dataInserter(sql, c);
                    break;
                case 19:
                    System.out.println("Insert the id of the character you want to insert:");
                    String stringCharacterId = scanner.nextLine();
                    if (stringCharacterId.equals("")) {
                        stringCharacterId = "null";
                    }
                    System.out.println("Insert the name of the character you want to insert:");
                    String characterName = scanner.nextLine();
                    if (characterName.equals("")) {
                        characterName = "null";
                    }
                    System.out.println("Insert the age of the character you want to insert:");
                    String age = scanner.nextLine();
                    if (age.equals("")) {
                        age = "null";
                    }
                    System.out.println("type true if your character is playable, else type false:");
                    boolean pBool;
                    String playability;
                    while (true) {
                        playability = scanner.nextLine();
                        if (playability.equalsIgnoreCase("true")) {
                            pBool = true;
                            break;
                        } else if (playability.equalsIgnoreCase("false")) {
                            pBool = false;
                            break;
                        } else if (playability.equals("")) {
                            playability = "null";
                            break;
                        } else {
                            System.out.println("Type true or false please");
                        }
                    }
                    sql = "INSERT INTO Character(CharacterID, Name, Age, Playability )"
                            + " values(" + stringCharacterId + ",'" + characterName + "', " + age + ", " + playability + ");";
                    dataInserter(sql, c);
                    break;
                case 20:
                    System.out.println("Insert the id of the company you want to insert:");
                    String stringCompanyId = scanner.nextLine();
                    if (stringCompanyId.equals("")) {
                        stringCompanyId = "null";
                    }
                    System.out.println("Insert the name of the company you want to insert:");
                    String companyName = scanner.nextLine();
                    if (companyName.equals("")) {
                        companyName = "null";
                    }
                    System.out.println("Insert the stock price of the company you want to insert:");
                    String stockPrice = scanner.nextLine();
                    if (stockPrice.equals("")) {
                        stockPrice = "null";
                    }
                    System.out.println("Insert the country of the company you want to insert:");
                    String country = scanner.nextLine();
                    if (country.equals("")) {
                        country = "null";
                    }
                    System.out.println("Insert the number of games made by the company you want to insert:");
                    String nogm = scanner.nextLine();
                    if (nogm.equals("")) {
                        nogm = "null";
                    }
                    System.out.println("Insert the founded Date of the company you want to insert:");
                    String foundedDate = scanner.nextLine();
                    if (foundedDate.equals("")) {
                        foundedDate = "null";
                    }
                    
                    sql = "insert into Company (CompanyID,Name,Numberofgamesmade,country,stockprice,foundationDate)"
                            + " values ("+stringCompanyId+",'"+companyName+"',"+nogm+",'"+country+"',"+stockPrice+",\""+foundedDate+"\");";
                    dataInserter(sql, c);
                    break;
                case 21:
                    System.out.println("Insert the id of the memory you want to insert:");
                    String stringMemoryId = scanner.nextLine();
                    if (stringMemoryId.equals("")) {
                        stringMemoryId = "null";
                    }
                    System.out.println("Insert the description of the memory you want to insert with less than 255 characters:");
                    String desc = scanner.nextLine();
                    if (desc.equals("")) {
                        desc = "null";
                    }
                    System.out.println("Insert the date of the memory you want to insert:");
                    String date = scanner.nextLine();
                    if (date.equals("")) {
                        date = "null";
                    }
                    System.out.println("Insert the id of the game which the memory you want to insert belongs to:");
                    String gameKey = scanner.nextLine();
                    if (gameKey.equals("")) {
                        gameKey = "null";
                    }
                    sql = "insert into Memory(MemoryID,Description,Date,GameKey)"
                            + " values("+stringMemoryId+",'"+desc+"',\""+date+"\","+gameKey+");";
                    dataInserter(sql, c);
                    break;
                case 22:
                    System.out.println("Insert the id of the OS you want to insert:");
                    String stringOSId = scanner.nextLine();
                    stringOSId = scanner.nextLine();
                    if (stringOSId.equals("")) {
                        stringOSId = "null";
                    }
                    System.out.println("Insert the name of the OS you want to insert:");
                    String osName = scanner.nextLine();
                    osName = scanner.nextLine();
                    if (osName.equals("")) {
                        osName = "null";
                    }
                    sql = "insert into OS(OSID,Name) "
                            + "values ("+stringOSId+",'"+osName+"');";
                    dataInserter(sql, c);
                    break;
                case 23:
                    System.out.println("Insert the id of the Platform you want to insert:");
                    String stringPlatformId = scanner.nextLine();
                    stringPlatformId = scanner.nextLine();
                    if (stringPlatformId.equals("")) {
                        stringPlatformId = "null";
                    }
                    System.out.println("Insert the name of the platform you want to insert:");
                    String platformName = scanner.nextLine();
                    if (platformName.equals("")) {
                        platformName = "null";
                    }
                    sql = "insert into OS(OSID,Name) "
                            + "values ("+stringPlatformId+",'"+platformName+"');";
                    dataInserter(sql, c);
                    break;
                case 24:
                    System.out.println("Insert the id of the Genre you want to insert:");
                    String stringGenreId = scanner.nextLine();
                    stringGenreId = scanner.nextLine();
                    if (stringGenreId.equals("")) {
                        stringGenreId = "null";
                    }
                    System.out.println("Insert the title of the genre you want to insert:");
                    String genreTitle = scanner.nextLine();
                    if (genreTitle.equals("")) {
                        genreTitle = "null";
                    }
                    System.out.println("Insert the score of the genre you want to insert:");
                    String genreScore = scanner.nextLine();
                    if (genreScore.equals("")) {
                        genreScore = "null";
                    }
                    sql = "insert into Genre values("+stringGenreId+",'"+genreTitle+"',"+genreScore+");";
                    dataInserter(sql, c);
                    break;
                case 25:
                    System.out.println("Insert the id of the row you want to insert:");
                    String stringSequelOfId = scanner.nextLine();
                    stringSequelOfId = scanner.nextLine();
                    if (stringSequelOfId.equals("")) {
                        stringSequelOfId = "null";
                    }
                    System.out.println("Insert the id of the game which is the sequel game:");
                    String sequelKey = scanner.nextLine();
                    if (sequelKey.equals("")) {
                        sequelKey = "null";
                    }
                    System.out.println("Insert the id of the game which is the prequel game");
                    String prequelKey = scanner.nextLine();
                    if (prequelKey.equals("")) {
                        prequelKey = "null";
                    }
                    sql = "insert into SequelOf(SequelOfID, SequelGameKey, BeingSequeledGameKey)"
                            + " values ("+stringSequelOfId+","+sequelKey+","+prequelKey+");";
                    dataInserter(sql, c);
                    break;
                case 26:
                    System.out.println("Insert the id of the row you want to insert:");
                    String stringProduceId = scanner.nextLine();
                    stringProduceId = scanner.nextLine();
                    if (stringProduceId.equals("")) {
                        stringProduceId = "null";
                    }
                    System.out.println("Insert the id of the game which is produced:");
                    String producedGameKey = scanner.nextLine();
                    if (producedGameKey.equals("")) {
                        producedGameKey = "null";
                    }
                    System.out.println("Insert the id of the company which has produced the game");
                    String companyKey = scanner.nextLine();
                    if (companyKey.equals("")) {
                        companyKey = "null";
                    }
                    sql = "insert into Produce(ProduceID, GameKey, CompanyKey)"
                            + " values ("+stringProduceId+","+producedGameKey+","+companyKey+");";
                    dataInserter(sql, c);
                    break;
                case 27:
                    System.out.println("Insert the id of the row you want to insert:");
                    String stringGameGenreId = scanner.nextLine();
                    stringGameGenreId = scanner.nextLine();
                    if (stringGameGenreId.equals("")) {
                        stringGameGenreId = "null";
                    }
                    System.out.println("Insert the id of the game:");
                    String gameOfGameGenreKey = scanner.nextLine();
                    if (gameOfGameGenreKey.equals("")) {
                        gameOfGameGenreKey = "null";
                    }
                    System.out.println("Insert the id of the genre:");
                    String genreKey = scanner.nextLine();
                    if (genreKey.equals("")) {
                        genreKey = "null";
                    }
                    sql = "insert into GameGenre (GameGenreID,GameKey,GenreKey)"
                            + " values ("+stringGameGenreId+","+gameOfGameGenreKey+","+genreKey+");";
                    dataInserter(sql, c);
                    break;
                case 28:
                    System.out.println("Insert the id of the row you want to insert:");
                    String stringGameCharId = scanner.nextLine();
                    stringGameCharId = scanner.nextLine();
                    if (stringGameCharId.equals("")) {
                        stringGameCharId = "null";
                    }
                    System.out.println("Insert the id of the game:");
                    String gameOfGameCharKey = scanner.nextLine();
                    if (gameOfGameCharKey.equals("")) {
                        gameOfGameCharKey = "null";
                    }
                    System.out.println("Insert the id of the genre:");
                    String charKey = scanner.nextLine();
                    if (charKey.equals("")) {
                        charKey = "null";
                    }
                    sql = "insert into GameCharacter(GameCharacterID,GameKey,CharacterKey)"
                            + " values ("+stringGameCharId+","+gameOfGameCharKey+","+charKey+");";
                    dataInserter(sql, c);
                    break;
                case 29:
                    System.out.println("Insert the id of the row you want to insert:");
                    String stringGameOSId = scanner.nextLine();
                    stringGameOSId = scanner.nextLine();
                    if (stringGameOSId.equals("")) {
                        stringGameOSId = "null";
                    }
                    System.out.println("Insert the id of the game:");
                    String gameOfGameOSKey = scanner.nextLine();
                    if (gameOfGameOSKey.equals("")) {
                        gameOfGameOSKey = "null";
                    }
                    System.out.println("Insert the id of the OS:");
                    String osKey = scanner.nextLine();
                    if (osKey.equals("")) {
                        osKey = "null";
                    }
                    sql = "insert into GameOS(GameOSID, GameKey, OSKey)"
                            + " values ("+stringGameOSId+","+gameOfGameOSKey+","+osKey+");";
                    dataInserter(sql, c);
                    break;
                case 30:
                    System.out.println("Insert the id of the row you want to insert:");
                    String stringGamePlatformId = scanner.nextLine();
                    stringGamePlatformId = scanner.nextLine();
                    if (stringGamePlatformId.equals("")) {
                        stringGamePlatformId = "null";
                    }
                    System.out.println("Insert the id of the game:");
                    String gameOfGamePlatformKey = scanner.nextLine();
                    if (gameOfGamePlatformKey.equals("")) {
                        gameOfGamePlatformKey = "null";
                    }
                    System.out.println("Insert the id of the Platform:");
                    String platformKey = scanner.nextLine();
                    if (platformKey.equals("")) {
                        platformKey = "null";
                    }
                    sql = "insert into GameOS(GameOSID, GameKey, OSKey)"
                            + " values ("+stringGamePlatformId+","+gameOfGamePlatformKey+","+platformKey+");";
                    dataInserter(sql, c);
                    break;
                default:
                    System.out.println("Please type a valid number.");
            }
            try {
                c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            chosen = scanner.nextInt();
        }

    }

    public static void queryExecuter(String sql, TableType type, Connection c) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            switch (type) {
                case Character:
                    while (rs.next()) {
                        String name = rs.getString("Name");
                        int id = rs.getInt("CharacterID");
                        int age = rs.getInt("Age");
                        boolean playability = rs.getBoolean("Playability");
                        System.out.println(name + " , " + id + " , " + age + " , "
                                + playability);
                    }
                case Game:
                    while (rs.next()) {
                        String name = rs.getString("Title");
                        int id = rs.getInt("GameID");
                        String release = rs.getString("ReleaseDate");
                        String finished = rs.getString("FinishedDate");
                        int serieLength = rs.getInt("SerieLength");
                        int score = rs.getInt("Score");
                        String pov = rs.getString("POV");
                        boolean mc = rs.getBoolean("MultiplayerCapability");
                        System.out.println(name + " , " + id + " , " + release + " , "
                                + finished + " , " + serieLength
                                + " , " + score + " , " + pov + " Has Multiplayer?: " + mc);
                    }
                case Genre:
                    while (rs.next()) {
                        String name = rs.getString("Title");
                        int id = rs.getInt("GenreID");
                        int score = rs.getInt("Score");
                        System.out.println(name + " , " + id + " , " + score);
                    }
                case Company:
                    while (rs.next()) {
                        String name = rs.getString("Name");
                        int id = rs.getInt("CompanyID");
                        int numberOfGames = rs.getInt("NumberOfGamesMade");
                        double stockPrice = rs.getDouble("StockPrice");
                        String country = rs.getString("Country");
                        String foundedDate = rs.getString("FoundationDate");
                        System.out.println(name + " , " + id + " , " + country
                                + " , " + stockPrice + " , " + numberOfGames + " , " + foundedDate);
                    }
                case Memory:
                    while (rs.next()) {
                        String description = rs.getString("Description");
                        int id = rs.getInt("MemoryID");
                        String date = rs.getString("Date");
                        System.out.println(description + " , " + id + " , " + date);
                    }
                case OS:
                    while (rs.next()) {
                        String name = rs.getString("Name");
                        int id = rs.getInt("OSID");
                        System.out.println(name + " , " + id);
                    }
                case Platform:
                    while (rs.next()) {
                        String name = rs.getString("Name");
                        int id = rs.getInt("PlatformID");
                        System.out.println(name + " , " + id);
                    }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static void dataInserter(String sql, Connection c) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.err.println("-----SQLException-----");
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("Message: " + ex.getMessage());
            System.err.println("Vendor: " + ex.getErrorCode());
        }
        
    }
}
