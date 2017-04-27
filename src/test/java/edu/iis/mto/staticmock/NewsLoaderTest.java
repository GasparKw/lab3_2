package edu.iis.mto.staticmock;

import static org.junit.Assert.*;

import edu.iis.mto.staticmock.reader.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class})


public class NewsLoaderTest {
	private NewsLoader newsLoader;
	private IncomingNews incomingNews;
	
	@Before
	public void setUp() {
		newsLoader = new NewsLoader();
		Configuration config = new Configuration();
		
		mockStatic(ConfigurationLoader.class);
		mockStatic(NewsReaderFactory.class);
		
		ConfigurationLoader mockLoader = mock(ConfigurationLoader.class);
		FileNewsReader mockFileNewsReader = mock(FileNewsReader.class);
		
		Whitebox.setInternalState(config, "readerType", "FileReader");
		
		incomingNews = new IncomingNews();
		incomingNews.add(new IncomingInfo("PublicMessage1", SubsciptionType.NONE));
		incomingNews.add(new IncomingInfo("PublicMessage2", SubsciptionType.NONE));
		incomingNews.add(new IncomingInfo("SubsriptionMessageA", SubsciptionType.A));
		incomingNews.add(new IncomingInfo("SubsriptionMessageB", SubsciptionType.B));
		incomingNews.add(new IncomingInfo("SubsriptionMessageC", SubsciptionType.C));
		
		when(ConfigurationLoader.getInstance()).thenReturn(mockLoader);
		when(mockLoader.loadConfiguration()).thenReturn(config);
		when(NewsReaderFactory.getReader("FileReader")).thenReturn(mockFileNewsReader);
		when(mockFileNewsReader.read()).thenReturn(incomingNews);
	}
	
	@Test 
	public void test_VerifyPublicMessages() {
		PublishableNews publishableNews = newsLoader.loadNews();
		assertThat(publishableNews.getPublicContent().size(), is(equalTo(2)));
		assertThat(publishableNews.getPublicContent().get(0), is(equalTo("PublicMessage1")));
		assertThat(publishableNews.getPublicContent().get(1), is(equalTo("PublicMessage2")));
	}
	
	@Test 
	public void test_VerifySubscribentMessages() {
		PublishableNews publishableNews = newsLoader.loadNews();
		assertThat(publishableNews.getSubsrcibentContent().size(), is(equalTo(3)));
		assertThat(publishableNews.getSubsrcibentContent().get(0), is(equalTo("SubsriptionMessageA")));
		assertThat(publishableNews.getSubsrcibentContent().get(1), is(equalTo("SubsriptionMessageB")));
		assertThat(publishableNews.getSubsrcibentContent().get(2), is(equalTo("SubsriptionMessageC")));
	}
	
	@Test
	public void test_VerifyGetReaderArgumant() {
		newsLoader.loadNews();
		verify(NewsReaderFactory.getReader("FileReader"), times(1));
	}
}
