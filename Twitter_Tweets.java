import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter_Tweets {
	//Access keys have been declared for gaining access to tweets
	private final static String Consumer_Key = "7HmKpSMc8u1ZMth71WUTCQWoc"; 
	private final static String Consumer_Secret = "HkePb2cANxclTvzkAoLmpDjsSBGSWOChX1sUYrP3ztk8W6kgNu";
	private final static String Access_Token = "782679996733149185-GdIDKqaYmbGJkY480Kw41VuhXNmriSJ"; 
	private final static String Access_Token_Secret = "UKm9LNfzkqfrRWIMBUzAOGiiM3yfo1mFsVPTLk9UJymgU";
	
	public static void main (String[] args) throws TwitterException, IOException {
		
		ConfigurationBuilder config = new ConfigurationBuilder();
		
		config.setDebugEnabled(true)
		         .setOAuthConsumerKey(Consumer_Key)
		         .setOAuthConsumerSecret(Consumer_Secret)
		         .setOAuthAccessToken(Access_Token)
		         .setOAuthAccessTokenSecret(Access_Token_Secret);

		TwitterFactory tfact = new TwitterFactory(config.build());
		twitter4j.Twitter twitter = tfact.getInstance();
		//Get tweets from Timeline
		Paging paging = new Paging();
		 paging.setPage(1);
		//Add the tweets appearing on rest of the pages. We will consider approximately 10 pages
		ResponseList<Status> stat = twitter.getHomeTimeline(paging);
		for(int i = 2; i < 11; i++)
		{
		 paging.setPage(i);
		 stat.addAll(twitter.getHomeTimeline(paging));
		}
		String final_tweets = "";
		int count = 1;
		for(Status status : stat)
		{
			    //Collect the most recent tweets (more than 100)
				if(status != null)
				{
					String single_tweet = "";
					//Display tweets with Usernames
					single_tweet = status.getUser().getName()+" -> "+ status.getText();
					final_tweets = final_tweets + "\n\nTweet Number " + count + " -> " + single_tweet; 	 
					}
				count++;
			}
		//Define the Output file position
		File file = new File("/home/cloudera/Output_Tweets.txt");
		if (!file.exists()) 
		{
			file.createNewFile(); //Create a new file, if the given file does not exist
		}
		FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter buffer = new BufferedWriter(writer);
		//Write the concatenated tweets to the file
		buffer.write("Following are most recent tweets (more than 100): \n");
		buffer.write(final_tweets);
		buffer.close();
		System.out.println("Please check the Output file Output_Tweets.txt at location /home/cloudera."); 
	}
}
