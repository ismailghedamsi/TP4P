package gui;
/**
* Classe MediaPlayerController qui contient les listeners controlant le gui 
* @author Melissa Jourdain
* @date h2016
*/
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.util.*;
import medias.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.beans.value.*;
import javafx.scene.media.*;

@SuppressWarnings("unchecked")
public class MediaPlayerController {
    MediaPlayerGui mGui;
    Player mPlayer;
    ProgressBarChange mProgressBarChanger;
    /**
    * Constructeur de la classe 
    * @param pGui le gui a controler
    * @param pPlayer le player affiche dans le gui
    */
    public MediaPlayerController(MediaPlayerGui pGui, 
            Player pPlayer){
        mGui = pGui;
        mPlayer = pPlayer;
        mProgressBarChanger = null;
    }

    /**
    * Classe permettant d'envoyer un fichier au serveur
    */
    public class AddToServerHandler implements EventHandler<ActionEvent>{
        private TableView<MediaPlayerGui.BasicMedia> mTable;
        public AddToServerHandler( TableView<MediaPlayerGui.BasicMedia> pTable ){
            mTable = pTable;
        }
        public void handle(ActionEvent event){
            System.out.println("Ajoute au serveur");
            ObservableList<Integer> mediasSelected = mTable.getSelectionModel().getSelectedIndices();
            int index = mediasSelected.get(0); 
            int sizeBefore = mPlayer.getSizeLibrary(MediaConstant.SERVER);
            mPlayer.addToServer(index);
            if(mPlayer.getSizeLibrary(MediaConstant.SERVER) != sizeBefore+1){
                System.out.println("probleme avec la fonction addToServer: la taille de la librairie ne semble augmente");
            }
            mGui.addToServer(mTable.getSelectionModel().getSelectedItem());
        }
    }
    /**
    * Classe permettant d'envoyer d'ajouter des item a la playlist du gui
    */
    public class AddToPlayListHandler implements EventHandler<ActionEvent>{
        private TableView<MediaPlayerGui.BasicMedia> mTable;
        private int mTypeLibrary;
        public AddToPlayListHandler(int pTypeLibrary, TableView<MediaPlayerGui.BasicMedia> pTable){
            mTypeLibrary = pTypeLibrary;
            mTable = pTable;
        }
        public void handle(ActionEvent event){
            ObservableList<Integer> mediasSelected = mTable.getSelectionModel().getSelectedIndices();
            int index = mediasSelected.get(0); 
            int sizeBefore = mPlayer.getSizeLibrary(MediaConstant.PLAYLIST);
            mPlayer.addToPlayList(mTypeLibrary,index);
            if(mPlayer.getSizeLibrary(MediaConstant.PLAYLIST) != sizeBefore+1){
                System.out.println("probleme avec la fonction addToPlayList: la taille de la librairie ne semble augmente");
            }
            mGui.addToPlayList(mTable.getSelectionModel().getSelectedItem());
        }
    }
    /**
    * Classe permettant d'afficher une seconde fenetre affichant toutes les informations reliees a un media donne
    */
    public class GetFullMediaInfoGetter implements EventHandler<ActionEvent>{
        private int mTypeLibrary;
        private TableView<MediaPlayerGui.BasicMedia> mTable;

        public GetFullMediaInfoGetter(int pTypeLibrary, TableView<MediaPlayerGui.BasicMedia> pTable){
            mTypeLibrary = pTypeLibrary;
            mTable = pTable;
        }
        public void handle(ActionEvent event){
             ObservableList<Integer> mediasSelected = mTable.getSelectionModel().getSelectedIndices();
            int index = mediasSelected.get(0); 
            String[] info = mPlayer.getFullMediaInfo(mTypeLibrary, index);               
            String[] fields = mPlayer.getFullFieldMediaInfo(mTypeLibrary, index);            
            if(info == null || info.length == 0){
                System.out.println("Erreur: la fonction getFullMediaInfo retourne un tableau null ou de taille 0");
            }
            if(fields == null || info.length == 0){
                System.out.println("Erreur: la fonction getFullFieldMediaInfo retourne un tableau null ou de taille 0");
            }
            if(fields.length != info.length){
                System.out.println("La taille des tableaux retournes par getFullMediaInfo et getFullFieldMediaInfo n'est pas la meme"); 
            }
            Stage stage = new MediaPlayerGui.FullMediaInfoWindow(fields,info);
            stage.show();
        }
    }
    /**
    * Fonction permettant d'effacer un item de la playlist
    */
    public class EraseHandler implements EventHandler<ActionEvent>{
        private int mTypeLibrary;
        private TableView<MediaPlayerGui.BasicMedia> mTable;
        public EraseHandler(int pTypeLibrary, TableView<MediaPlayerGui.BasicMedia> pTable){
            mTypeLibrary = pTypeLibrary;
            mTable = pTable;
        }
        public void handle(ActionEvent event){
            System.out.println("Efface de la librairie");
            ObservableList<Integer> mediasSelected = mTable.getSelectionModel().getSelectedIndices();
            int index = mediasSelected.get(0); 
            int sizeBefore = mPlayer.getSizeLibrary(MediaConstant.PLAYLIST);
            mPlayer.deleteFromPlaylist(index);
            if(mPlayer.getSizeLibrary(MediaConstant.PLAYLIST) != sizeBefore-1){
               System.out.println("Erreur: la taille de la playlist apres un appel a la fonction deleteFromPlaylist ne semble pas diminuer"); 
            }
            mGui.removeFromPlayList(index); 
            mGui.mMediaPlayers.remove(index);
            if(mPlayer.getCurrentPlayed() == index)
                MediaPlayerController.this.nextMedia();
        }
    }
    /**
    * Fonction permettant de passer au prochain media
    */
    public void nextMedia(){
        mPlayer.next();
        ProgressBarChange newProgressBarChanger = new ProgressBarChange();
        mGui.setMediaToPlay(mPlayer.getCurrentPlayed(), mProgressBarChanger, newProgressBarChanger);
        mProgressBarChanger = newProgressBarChanger;
    }
    /**
    * Fonction permettant de passer au media precedent
    */
    public void previousMedia(){
        mPlayer.previous();
        ProgressBarChange newProgressBarChanger = new ProgressBarChange();
        mGui.setMediaToPlay(mPlayer.getCurrentPlayed(), mProgressBarChanger, newProgressBarChanger);
        mProgressBarChanger = newProgressBarChanger;
    }
    public class MediaNextChanger implements Runnable{
        public void run(){
            MediaPlayerController.this.nextMedia();
        }
    }
    /**
    * Classe responsable de la gestion des cliques sur les boutons play/pause
    */
    public class PlayPause implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            Button clicked = (Button)(e.getSource());         
            if(clicked.getText().equals("pause")){
                clicked.setText("play");
                if(mGui.mMediaView.getMediaPlayer() != null)
                    mGui.mMediaView.getMediaPlayer().pause();
            }
            else{
                clicked.setText("pause");
                if(mGui.mMediaView.getMediaPlayer() != null)
                    mGui.mMediaView.getMediaPlayer().play();
                else{ 
                    mPlayer.setCurrentPlayed(0);
                    ProgressBarChange newProgressBarChanger = new ProgressBarChange();
                    mGui.setMediaToPlay(mPlayer.getCurrentPlayed(), mProgressBarChanger, newProgressBarChanger);
                    mProgressBarChanger = newProgressBarChanger;
                }
            }
        }
    }
    /**
    * Classe gerant l'evenement 'clique sur le bouton previous'
    */
    public class PreviousSong implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            previousMedia();
        }
    }
     /**
    * Classe gerant l'evenement 'clique sur le bouton next'
    */
   public class NextSong implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            nextMedia();
        }
    }
    /**
    * Classe gerant la barre de progression affichant dans le panneau du player
    */
    public class ProgressBarChange implements ChangeListener<Duration>{
        public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
            MediaPlayer mediaPlayer = mGui.mMediaView.getMediaPlayer();
            if(mediaPlayer != null)
                mGui.mTabPlayer.mProgressBar.setProgress(1.0 * mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis());
        } 
    }
    /**
    * Classe gerant l'evenement double-clique sur un item de la playlist
    */
    public class PlayListPick implements EventHandler<MouseEvent>{

        TableView<MediaPlayerGui.BasicMedia> mTable;
        public PlayListPick(TableView<MediaPlayerGui.BasicMedia> pTable){
            super();
            mTable = pTable;
        }
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    ObservableList<Integer> mediasSelected = mTable.getSelectionModel().getSelectedIndices();
                    int index = mediasSelected.get(0); 
                    mPlayer.setCurrentPlayed(index);
                    ProgressBarChange newProgressBarChanger = new ProgressBarChange();
                    mGui.setMediaToPlay(mPlayer.getCurrentPlayed(), mProgressBarChanger, newProgressBarChanger);
                    mProgressBarChanger = newProgressBarChanger;
                }
            }
        }
    }
}
