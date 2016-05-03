package gui;
/**
* Classe MediaPlayerGui qui constitue un player de fichier media 
* @author Melissa Jourdain
* @date h2016
*/
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.*;
import java.util.*;
import medias.Player;
import medias.MediaConstant;
import javafx.scene.media.*;
import java.io.*;
@SuppressWarnings("unchecked") 

public class MediaPlayerGui extends Application {
    //les options des differents menus contextuels
    private static String[] contextMenuLocal = {"Ajouter au serveur", "Ajouter a la playlist", "Information sur le media"};
    private static String[] contextMenuServeur = {"Ajouter a la playlist","Information sur le media"};
    private static String[] contextMenuPlaylist = {"Effacer","Information sur le media"};
    //les listes des librairies media
    private ObservableList<BasicMedia> mLocalLibrary;
    private ObservableList<BasicMedia> mServerLibrary;
    private ObservableList<BasicMedia> mPlaylist;
    //vecteur des differents mediaPlayer formant la playlist
    Vector<MediaPlayer> mMediaPlayers;
    // le panneau affichant le player avec les controles play,pause,...
    PlayerPanel mTabPlayer;
    // la vue sur le media qui joue 
    MediaView mMediaView;
    //le controleur, contenant toutes les listeners
    MediaPlayerController mController;
    //une instance de la classe Player
    Player mPlayer;
    /**
    * Constructeur vide de la classe 
    */
    public MediaPlayerGui(){
        super();
        mMediaPlayers = new Vector<MediaPlayer>();
    }
    /**
    * Fonction appelee automatiquement par le main. Permet d'initialiser les 
    * variables membres et non graphiques de la classe, a partir du tableau args recu en 
    * parametre du main
    */
    public void init(){
        List<String> args = getParameters().getUnnamed(); 
        if(args.size()== 3){
            mPlayer = new Player(args.get(0), args.get(1), Integer.parseInt(args.get(2))); 
        } 
        else{
            mPlayer = new Player(args.get(0)); 
        }
        mController = new MediaPlayerController(this, mPlayer);
    }
    /**
    * Fonction appelee automatiquement par le main qui initialise les differents 
    * objets graphiques et les listeners associes
    * @param stage le contexte graphique
    @override
    */
    public void start(Stage stage){ 
        StackPane root = new StackPane();

        stage.setTitle("Super Media Player");
        stage.setWidth(700);
        stage.setHeight(700);
        stage.setResizable(false);
        TabPane tabPane = new TabPane();

        Tab tabLocalLibrary = new MediaPanel(MediaConstant.LOCAL,"Librairie locale",getLibrary(MediaConstant.LOCAL));
        Tab tabServerLibrary = new MediaPanel(MediaConstant.SERVER,"Librairie serveur",getLibrary(MediaConstant.SERVER));
        Tab tabPlaylist = new MediaPanel(MediaConstant.PLAYLIST,"Liste courante ",getLibrary(MediaConstant.PLAYLIST));
        mTabPlayer = new PlayerPanel();

        tabPane.getSelectionModel().select(1);
        tabPane.getTabs().addAll(tabLocalLibrary, tabServerLibrary, tabPlaylist, mTabPlayer);

        root.getChildren().add(tabPane);
        Scene scene = new Scene(root, 800,800);

        stage.setScene(scene);
        stage.show();
    }
    /**
    * Fonction appelee lors de la fermeture de la fenetre du gui
    */
    public void stop(){
       mPlayer.close(); 
    }
    /**
    * Fonction permettant de changer le media qui joue actuellement
    * @param pIdx l'index du nouveau media a jouer
    * @param oldProgressBarChange la barre de progres ancienne
    * @param newProgressBarChange la nouvelle barre de progres
    */
    public void setMediaToPlay(int pIdx, MediaPlayerController.ProgressBarChange oldProgressBarChange, MediaPlayerController.ProgressBarChange newProgressBarChange ){
        if(pIdx >= mPlaylist.size())
            return;
        if(mMediaView.getMediaPlayer() != null){
            mMediaView.getMediaPlayer().currentTimeProperty().removeListener(oldProgressBarChange);
        }
        mTabPlayer.mPlayPause.setText("pause");
        if(mMediaView.getMediaPlayer() != null)
            mMediaView.getMediaPlayer().stop();
        mMediaView.setMediaPlayer(mMediaPlayers.get(pIdx));
        mMediaView.getMediaPlayer().currentTimeProperty().addListener(newProgressBarChange);
         mMediaView.setFitWidth(500);
         mMediaView.setFitHeight(500);
         mMediaView.setPreserveRatio(true);
         mTabPlayer.mTitlePlayed.setText(mPlaylist.get(pIdx).getTitre()); 
         mMediaView.getMediaPlayer().play();

    }
    /**
    * Fonction d'initialisation des colonnes tables 
    * @param pTable la table pour laquelle on doit initialiser les colonnes
    */
    private void initFieldTableMedia(TableView<BasicMedia> pTable){
        pTable.setEditable(false);
        pTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        String[] columnTitle = mPlayer.getBasicLibraryFieldInfo();
        if(columnTitle == null||columnTitle.length == 0){
            System.out.println("la fonction getBasicLibraryInfo de la classe Player retourne un tableau vide ou null");
            System.out.println("Impossible de creer les tables a afficher dans le gui");
            System.exit(0);
        }
        TableColumn<BasicMedia,String> column = null;
        for(int i = 0; i<columnTitle.length; i++){
            column = new TableColumn(columnTitle[i]);
            column.setMinWidth(200);
            column.setCellValueFactory(
                    new PropertyValueFactory<BasicMedia, String>(columnTitle[i]));
            pTable.getColumns().add(column);
        }
    }
    /**
    * FOnction permettant d'ajouter un nouvel item a la playlist
    * @param pMedia l'item a ajouter
    */
    public void addToPlayList(BasicMedia pMedia){
        mPlaylist.add(pMedia);
        String fileName = mPlayer.getFileNameFromMedia(MediaConstant.PLAYLIST,mPlaylist.size()-1);
        if(fileName == null){
            System.out.println("impossible d'ajouter le fichier a la playlist: la fonction getFileNameFromMedia retourne une chaine null");
        }
        try{
            Media media = new Media(new File(fileName).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mMediaPlayers.add(mediaPlayer);

            mediaPlayer.setOnEndOfMedia(mController.new MediaNextChanger());        
        }catch(Exception e){
            System.out.println("Nom de fichier invalide: "+fileName);
            e.printStackTrace();
        }
    }
    /**
    * Fonction permettant d'effacer un item de la playlist
    * @pIdx l'index de l'item a retirer
    */
    public void removeFromPlayList(int pIdx){
        mPlaylist.remove(pIdx);
    }
    /**
    * Fonction permettant d'ajouter un fichier a la table du serveur
    * @param pMedia le media a ajouter
    */
    public void addToServer(BasicMedia pMedia){
        mServerLibrary.add(pMedia);   
    }
    /**
    * Fonction d'acces a la liste des medias d'une librairie
    * @pTypeLibrary le type de la librairie (local, serveur ou playlist)
    * @return ObservableList<BasicMedia> la liste  
    */
    private ObservableList<BasicMedia> getLibrary(int pTypeLibrary){
        ObservableList<BasicMedia> library =FXCollections.observableArrayList();
        switch(pTypeLibrary){
            case MediaConstant.LOCAL:
                mLocalLibrary = library;
                break;
            case MediaConstant.SERVER:
                mServerLibrary = library;
                break;
            case MediaConstant.PLAYLIST:
                mPlaylist = library;
                break;
            default:
                System.out.println("Type de librairie invalide (getLibrary)");
        }
        String[][] mediaInfo = mPlayer.getBasicLibraryInfo(pTypeLibrary);
        if(mediaInfo.length != mPlayer.getSizeLibrary(pTypeLibrary)){
            System.out.print("la taille du tableau retournee par getBasicLIbraryInfo(pTypeLibrary) est: "+mediaInfo.length+" et la taille de la librairie est de: "+mPlayer.getSizeLibrary(pTypeLibrary)+"Le probleme est pour la librairie de type:");
            switch(pTypeLibrary){
                case MediaConstant.LOCAL:
                    System.out.println("locale");
                    break;
                case MediaConstant.SERVER:
                    System.out.println("serveur");
                    break;
                case MediaConstant.PLAYLIST:
                    System.out.println("playlist");
                    break;
                default:
                    System.out.println("Type de librairie invalide (getLibrary)");
            }
            
        }
        for(int i = 0; i<mediaInfo.length;i++){ 
            library.add(new BasicMedia(mediaInfo[i][MediaConstant.TITLE],
                        mediaInfo[i][MediaConstant.YEAR],
                        mediaInfo[i][MediaConstant.DURATION] 
                        ));
        }
        return library;

    }

    /**
    * Classe representant un menu contextuel
    */
    public class MediaContextMenu extends ContextMenu{
        /**
        * Constructeur 
        * @param pTypeLibrary la librairie pour laquelle on veut ajouter un menu contextuel aux items qui la composent
        * @param pTable la table pour laquelle on veut ajouter un menu contextuel aux items
        * @param options les options qui forment le menu contextuel
        */
        public MediaContextMenu(int pTypeLibrary, TableView<BasicMedia> pTable, String[] options){
            MenuItem item;
            for(int i = 0; i<options.length; i++){
                item = new MenuItem(options[i]);
                getItems().add(item);
                switch(options[i]){
                    case "Ajouter au serveur" :
                        item.setOnAction(mController.new AddToServerHandler(pTable));
                        break;
                    case "Ajouter a la playlist":
                        item.setOnAction(mController.new AddToPlayListHandler(pTypeLibrary, pTable));
                        break;
                    case "Effacer":
                        item.setOnAction(mController.new EraseHandler(pTypeLibrary, pTable));
                        break;
                   case "Information sur le media":
                        item.setOnAction(mController.new GetFullMediaInfoGetter(pTypeLibrary, pTable));
                        break;
                    default:
                        System.out.println("Menu contextuel: option invalide");
                }
            }
        }
    }
    /**
    * Classe representant la fenetre qui sera affichee lorsque l'utilisateur veut avoir toutes les informations d'un fichier media
    */
    public static class FullMediaInfoWindow extends Stage{
        /**
        * Constructeur
        * @param pFields les champs d'information qui seront affichees
        * @param pValues les valeurs pour chacun des champs d'information
        */
        public FullMediaInfoWindow(String[] pFields, String[] pValues){
            setTitle("Information");
            GridPane grid = new GridPane();
            grid.getColumnConstraints().add(new ColumnConstraints(200));
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(5);
            grid.setHgap(5);  
            Label labelField, labelValue; 
            for(int i = 0; i<pFields.length; i++){
                labelField = new Label(pFields[i]);
                labelValue= new Label(pValues[i]);
                labelValue.setWrapText(true);
                grid.add(labelField,0,i);
                grid.add(labelValue,1,i);
            }
            Scene scene = new Scene(grid, 500,400);

            setScene(scene);
            
        }
    }
    /**
    * Classe representant le panneau affichant le player avec ses controles
    */
    public class PlayerPanel extends Tab{
        Button mPlayPause, mNext, mPrevious;
        ProgressBar mProgressBar; 
        Label mTitlePlayed; 
        /**
        * Constructeur initialisant les objets graphiques du panneau
        * et les listeners associes
        */
        public PlayerPanel(){
            super("Player");
            BorderPane layout = new BorderPane();
            mTitlePlayed = new Label("");
            layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
            mMediaView = new MediaView();
            mProgressBar = new ProgressBar();
            mPlayPause = new Button("play");
            mPlayPause.setOnAction(mController. new PlayPause());
            mNext = new Button("next");
            mNext.setOnAction(mController. new NextSong());
            mPrevious = new Button("previous");
            mPrevious.setOnAction(mController. new PreviousSong());

            mProgressBar.setMaxWidth(Double.MAX_VALUE);
            
            HBox controlBox= new HBox(10);
            controlBox.setAlignment(Pos.CENTER);
            controlBox.getChildren().setAll(mNext, mPlayPause, mPrevious);
            
            VBox mediaBox = new VBox(10);
            mediaBox.setAlignment(Pos.CENTER);
            mediaBox.getChildren().setAll(mMediaView, mTitlePlayed);
            layout.setTop(controlBox);
            layout.setCenter(mediaBox);
            layout.setBottom(mProgressBar);
            this.setContent(layout);
           } 
    }

    /**
    * Classe qui represente un panneau affichant une des librairies
    */
    public class MediaPanel extends Tab{
        private TableView<BasicMedia> mTable ;
        private ObservableList<BasicMedia> mLibrary;

        /**
        * Constructeur de la classe 
        * @param pTypeLibrary le type de librairie (local, serveur ou playlist)
        * @param pTitle le titre du panneau
        * @param pLibrary la librairie a afficher dans le panneau
        */
        public MediaPanel(int pTypeLibrary, String pTitle, ObservableList<BasicMedia> pLibrary){
            setText(pTitle);
            mTable = new TableView<BasicMedia>();
            mTable.setEditable(false);
            initFieldTableMedia(mTable);
            mLibrary = pLibrary;
            mTable.setItems(mLibrary);
            setContent(mTable);
            switch(pTypeLibrary){
                case MediaConstant.LOCAL:
                    mTable.setContextMenu(new MediaContextMenu(MediaConstant.LOCAL, mTable, contextMenuLocal));
                    break;
                case MediaConstant.SERVER:
                    mTable.setContextMenu(new MediaContextMenu(MediaConstant.SERVER, mTable, contextMenuServeur));
                    break;
                case MediaConstant.PLAYLIST:

                    mTable.setContextMenu(new MediaContextMenu(MediaConstant.PLAYLIST,mTable, contextMenuPlaylist));
                    mTable.setRowFactory( mTable -> {
                            TableRow<BasicMedia> row = new TableRow<>();
                            row.setOnMouseClicked(mController.new PlayListPick(mTable));
                            return row ;
                            });
                    break;
                default:
                    System.out.println("Type de librairie invalide: MediaPanel");
                    System.exit(0);
            }
        }
    }
    /**
    * Classe representant un media dnas les tables a afficher
    */
    public static class BasicMedia{
        private SimpleStringProperty Titre;
        private SimpleStringProperty Annee;
        private SimpleStringProperty Duree;
        private BasicMedia(String pTitle, String pYear, String pDuration) {
            this.Titre = new SimpleStringProperty(pTitle);
            this.Annee = new SimpleStringProperty(pYear);
            this.Duree= new SimpleStringProperty(pDuration);
        }

        public String getTitre() {
            return Titre.get();
        }

        public String getAnnee() {
            return Annee.get();
        }

        public String getDuree() {
            return Duree.get();
        }
        public String toString(){
            return Titre+" "+Annee+" "+Duree;
        }

    }

} 
