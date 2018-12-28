package nl.denhaag.rest.monitor.folder;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowDeps {
	
	private static final Logger logger = LogManager.getLogger();
	private static ArrayList<PathList> pl = new ArrayList<PathList>();
	public static String res;
	
	private static void show (Dependencies d, Optional <String> history, int i) {
		logger.debug("ShowDeps.show: start");
		String h = history.isPresent() ? history.get() : "";
		for (Dependency dd :d.getDependencies()){
			logger.trace("ShowDeps.show: start loop dependency "+dd.getId());
			PathList p = new PathList();
			p.setId(dd.getId());
			p.setParentid(h);
			p.setPathname(dd.getName());
			pl.add(p);
			if (dd.getDependencies() != null){
				show (dd.getDependencies(), Optional.of(dd.getId()), i+1);
			}
			logger.trace("ShowDeps.show: end loop dependency "+dd.getId());
		}
		logger.debug("ShowDeps.show: end");
	}

	public static void noshow (Dependencies d, Optional <String> history, int i){
		logger.info("ShowDeps.noshow: start");
		show (d, history, i);
		logger.info("ShowDeps.noshow: end");
	}
	
	
	public static void search (String id, String path) throws RuntimeException{
		logger.info("ShowDeps.search: start");
		for (PathList p : pl){
			logger.trace("ShowDeps.search: start loop PathList "+p.getPathname());
			if (p.getId().equals(id)){
				if (p.getParentid().equals("")){
					if (path.equals("")){
						res = "/"+p.getPathname();
					} else {
						res = "/"+p.getPathname()+"/"+path;
					}
					logger.trace("ShowDeps.search: break loop and method");
					throw new RuntimeException ("We do not care "+res);
				}
				if (path.equals("")){
					search (p.getParentid(),p.getPathname());
				} else {
					search (p.getParentid(),p.getPathname()+"/"+path);
				}
			}
			logger.trace("ShowDeps.search: end loop PathList "+p.getPathname());
		}
		logger.info("ShowDeps.search: end");
	}

}
