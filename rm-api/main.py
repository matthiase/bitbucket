from google.appengine.ext import webapp
from google.appengine.ext.webapp import util
from controllers import RegionHandler, DealerHandler, DealerRegionHandler

application = webapp.WSGIApplication([
	('/regions', RegionHandler),
	('/regions/(.+)', RegionHandler),
	('/dealers', DealerHandler),
	('/dealers/region/(.+)', DealerRegionHandler),	
	('/dealers/(.+)', DealerHandler),	
	], debug=True)

def main():		
  util.run_wsgi_app(application)


if __name__ == '__main__':
  main()
