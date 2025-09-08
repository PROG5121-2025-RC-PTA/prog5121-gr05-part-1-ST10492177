
public class SeriesModel {
    public String SeriesId;
    public String SeriesName;
    public String SeriesAge;
    public String seriesNumberOfEpisodes;
    
    
    public SeriesModel(String id, String name, String age, String episodes){
            this.SeriesId = id;
            this.SeriesName = name;
            this.SeriesAge = age;
            this.seriesNumberOfEpisodes = episodes;
    
}
    @Override
    public String toString(){
        return "Series ID:" + SeriesId + "\n" +
                "Name:" + SeriesName + "\n" +
                "Age Restriction:" + SeriesAge + "\n" +
                "Episodes:" + seriesNumberOfEpisodes;
              
    }
}
