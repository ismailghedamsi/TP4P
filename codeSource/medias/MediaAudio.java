public abstract class MediaAudio extends Media{
	  private String mArtist;
	  private String mAlbum;
	  
	  public MediaAudio(String pFileName,int pDuration,int pYear,String pTitle,String pArtist,String pAlbum){
		  super(pFileName,pDuration,pYear,pTitle);
		  mArtist = pArtist;
		  mAlbum = pAlbum;
	  }
	  
	  public MediaAudio(MediaAudio pMedia){
		  super(pMedia);
		  this.mArtist = pMedia.mArtist;
		  this.mAlbum = pMedia.mAlbum;
	  }
}
