package User;
public class Manual {
    String heading;
    String brif;
    int titleImage;
    boolean visibilty;
    public Manual(String heading, String brif, int titleImage) {
        this.heading = heading;
        this.brif = brif;
        this.titleImage = titleImage;
        this.visibilty=false;
    }
public boolean isVisibilty(){
        return visibilty;
}

    public void setVisibilty (boolean visibilty){
        this.visibilty=visibilty;
    }
}
