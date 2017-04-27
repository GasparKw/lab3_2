package edu.iis.mto.staticmock;

import static org.junit.Assert.*;

import edu.iis.mto.staticmock.reader.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class})


public class NewsLoaderTest {
	
	@Before
	public void setUp() {
		
	}

	@Test
	public void test_loadNews() {
		
	}
}
