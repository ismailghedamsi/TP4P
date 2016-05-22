package medias;
//import sockets.*;
import java.util.*;
import java.io.*;
public class Player{
    //la librairie locale du client
    private MediaLibrary mLocalLib;
    //la librairie du serveur
    //private MediaLibrary mServerLib;
    //la playlist courante
    private MediaLibrary mPlayList;
    //les fichiers recus du serveur seront sauvegardes dans ce repertoire
    private static final String TMP_DIRECTORY = "./tmp";
    //l'index, dans la playlist, du fichier en train de jouer. 
    private int mCurrentPlayed;
    //la connexion vers le serveur
    //private MediaClientSocket mClient;
    private String mFileNameLocalLib;
    /**
    * Constructeur de la classe qui prend en parametre le fichier de 
    * description de la librairie locale du client
    * Ce constructeur n'initialise pas de connexions avec un serveur
    * @param pFileNameLocalLib le fichier de description de la librairie locale
    */
    public Player(String pFileNameLocalLib){
		Scanner pScan;
       mFileNameLocalLib = pFileNameLocalLib; 
       try{
		   pScan = new Scanner(new File("../libLocal.txt"));
		     mLocalLib = new MediaLibrary(pScan);
	   }catch(IOException ioe){
		   ioe.getMessage();
	   }
       mCurrentPlayed=-1;
     
       
       mPlayList = new MediaLibrary();
       
    }
    /**
    * Constructeur de la classe qui initialise la librairie locale, et permet une connexion
    * avec un serveur 
    * @param pFileNameLocalLib le fichier de description de la librairie locale
    * @param pServerAddress l'adresse ip du serveur
    * @param pServerPort le numero de port sur lequel le serveur ecoute
    */
    public Player(String pFileNameLocalLib,String pServerAddress, int pServerPort){
    }
    /**
    * Fonction permettant d'effacer un item de la playlist
    * @param pIdx l'index de l'item a effacer. La fonction ne fait 
    * rien si l'index est invalide
    */
    public void deleteFromPlaylist(int pIdx){
		mPlayList.remove(pIdx);
    }
    /**
    * Fonction d'acces au nom du fichier complet (path+nom fichier) du media 
    * qui est en train de jouer
    * @return String le nom du fchier 
    */
    public String getCurrentFileNamePlayed(){
        return mPlayList.get(mCurrentPlayed).getFileName();
    }
    /**
    * Fonction d'acces au nom d'un fichier donne d'une librairie donne
    * @param pTypeLib qui prendra la valeur MediaConstant.LOCAL, SERVER ou PLAYLIST
    * @param pIdx l'index du fichier 
    * @return le nom du fichier 
    */
    public String getFileNameFromMedia(int pTypeLib, int pIdx){
		if(pTypeLib == MediaConstant.LOCAL){
			return mLocalLib.get(pIdx).getTitle();
		}else if(pTypeLib == MediaConstant.PLAYLIST){
			return mPlayList.get(pIdx).getTitle();
		}
        return mPlayList.get(pIdx).getTitle();
    }
    /**
    * Fonction permettant de changer l'index en train de jouer pour le suivant. 
    * Si on est a la fin de la playlist, on recommence au debut
    */
    public void next(){
		if(mCurrentPlayed < mPlayList.getMediaList().size()){
			mCurrentPlayed++;
		}else{
			mCurrentPlayed=0;
		}
        
    }

    /**
    * Fonction permettant de changer l'index en train de jouer pour le suivant. 
    * Si on est au debut de la playlist, on recommence a la fin
    */
    public void previous(){
		if(mCurrentPlayed > mPlayList.getMediaList().size()){
			mCurrentPlayed--;
		}else{
			mCurrentPlayed = mPlayList.getMediaList().size()-1;
		}
    }
    /**
    * Fonction d'acces a l'index du media qui joue 
    * @return int l'index du media qui joue presentement
    */
    public int getCurrentPlayed(){
        return mCurrentPlayed;
    }
    /**
    * Fonction permettant de changer la valeur de l'index du fichier qui joue presentement
    * Cette fonction est appelee par l'interface graphique
    * @param pCurrentPlayed le nouvel index du media a faire jouer
    */
    public void setCurrentPlayed(int pCurrentPlayed){
		mCurrentPlayed =pCurrentPlayed;
    }

    /**
    * Fonction permettant d'ajouter un nouvel item a la playlist
    * Si la source est la librairie du serveur, il faut telecharger le fichier correspondant 
    * dans le repertoire TMP_DIRECTORY
    * @param pLibrarySource la librairie (local ou serveur) de source du fichier a ajouter
    * @param pIdx l'index de l'item a ajouter dans la librairie de source
    */
    public void addToPlayList(int pLibrarySource, int pIdx){
		if(pLibrarySource == MediaConstant.LOCAL){
			mPlayList.add(mLocalLib.get(pIdx));
		}
    }
    /**
    * Fonction permettant d'ajouter l'item de la librairie locale a l'index pIdx, a la 
    * librairie du serveur. Il faudra envoyer le fichier correspondant pour qu'il soit 
    * sur le serveur
    * @param pIdx l'index du fichier a envoyer au serveur
    */
    public void addToServer(int pIdx){
    }
    /**
    * Fonction d'acces a la taille de la librairie. 
    * La valeur actuellement retournee est de 3 seulement pour afficher des valeurs par defaut 
    * dans l'interface graphique, en attendant que vous ayiez complete le code de la classe
    * @param pTypeLib le type de librairie (LOCAL, SERVER, PLAYLIST) pour laquelle on veut obtenir la taille
    */
    public int getSizeLibrary(int pTypeLib){
		if(pTypeLib == MediaConstant.LOCAL){
			return mLocalLib.getMediaList().size();
		}else if(pTypeLib == MediaConstant.PLAYLIST){
			return mPlayList.getMediaList().size();
		}
        return 3;
    }
    /**
    * Fonction permettant d'obtenir les champs d'informations qui apparaitront dans les colonnes de l'interface 
    * graphique
    * Le tableau est actuellement "hardcode" mais il devrait etre exactement le meme en appelant la 
    * fonction getBasicFIeldInfo de la classe MediaLibrary
    * @return String[] les titres de chacune des colonnes qui sera affiches dans le gui
    */
    public String[] getBasicLibraryFieldInfo(){
        return mLocalLib.getBasicFieldInfo();
    }
    /**
    * Fonction permettatn d'obtenir, pour chacun des medias d'une librairie donnee (LOCAL, SERVER ou PLAYLIST), 
    * la valeur pour chacun des champs de bases a afficher dans l'interface graphique
    * @param pTypeLib la librairie pour laquelle on desire obtenir les informations pour chacun des items 
    * @return les informations de chacun des items de la librairie 
    */
    public String[][] getBasicLibraryInfo(int pTypeLib){
        String[][] result = {{"(item 1:)valeur colonne 1", "(item 1:)valeur colonne 2", "(item 1:)valeur colonne 3"},
            {"(item 2:)valeur colonne 1", "(item 2:)valeur colonne 2", "(item 2:)valeur colonne 3"}, 
            {"(item 2:)valeur colonne 1", "(item 2:)valeur colonne 2", "(item 2:)valeur colonne 3"}}; 
           
          if(pTypeLib == MediaConstant.LOCAL){
			  result =   mLocalLib.getBasicLibraryInfo();
		  }else if(pTypeLib == MediaConstant.PLAYLIST ){
			  result =  mPlayList.getBasicLibraryInfo();
		  }
        return result;
    }
    /**
    * Fonction retournant tous les champs d'information disponibles pour un media donne
    * Les champs disponibles sont differents si on a un media video ou audio
    * @param pTypeLib la librairie du media pour lequel on obtient l'information
    * @param pIdx l'index de l'item dans la librairie
    * @return String[] tous les champs d'informations disponibles pour le media donne 
    */
    public String[] getFullFieldMediaInfo(int pTypeLib, int pIdx){
        String[] result = new String[6];
        if(pTypeLib == MediaConstant.LOCAL){
		
			if(mLocalLib.getMediaList().get(pIdx) instanceof MediaVideo){
				 result = mLocalLib.getVideoFieldInfo();
			}else{
				result = mLocalLib.getAudioFiledInfo();
			}
		}else if(pTypeLib == MediaConstant.PLAYLIST){
			if(mLocalLib.getMediaList().get(pIdx) instanceof MediaVideo){
				 result = mPlayList.getVideoFieldInfo();
			}else{
				result = mPlayList.getAudioFiledInfo();
			}
		}
        return result;
    }
    /**
    * Fonction retournant toutes les informations disponibles pour un media donne
    * Les informations disponibles sont differents si on a un media video ou audio
    * @param pTypeLib la librairie du media pour lequel on obtient l'information
    * @param pIdx l'index de l'item dans la librairie
    * @return String[] tous les champs d'informations disponibles pour le media donne 
    */
    public String[] getFullMediaInfo(int pTypeLib, int pIdx){
        String[] result = {"valeur champ1", "valeur champ2", "valeur champ3", "valeur champ4", "valeur champ3", "valeur champ4"};
        if(pTypeLib == MediaConstant.LOCAL){
				 result = mLocalLib.getFullMediaInfo(pIdx);
		}else if(pTypeLib == MediaConstant.PLAYLIST){
			result = mPlayList.getFullMediaInfo(pIdx);
		}
        return result;
    }
    /**
    * Fonction appele lors de la fermeture de l'interface graphique. Cette fonction doit:
    * 1. fermer les connexions 
    * 2. effacer les fichiers recus du serveur
    */
    public void close() {
    }
}
