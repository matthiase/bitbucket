from google.appengine.ext import webapp
from google.appengine.ext import db
import simplejson as json
from models import Dealer, Region

class RegionHandler(webapp.RequestHandler):	
	def get(self, id=None):
		if id == None:
			data = []
			query = db.GqlQuery("SELECT * FROM Region ORDER BY country, code")
			for region in query:
				data.append(region.entity())
		else:
			data = None
			query = db.Query(Region)
			query.filter('code = ', id.upper())
			region = query.get()
			if region != None:
				data = region.entity()
			
		if data:
			callback = self.request.get('jsoncallback')
			if callback:
				response = "%s(%s)" % (callback, json.dumps(data))
			else:
				response = json.dumps(data)
			self.response.out.write(response);


class DealerHandler(webapp.RequestHandler):
	def get(self, id=None):
		if id == None:
			data = []
			query = db.GqlQuery("SELECT * FROM Dealer ORDER BY name")
			for dealer in query:
				data.append(dealer.entity())
		else:
			data = None
			dealer = Dealer.get_by_id(int(id))
			if dealer != None:
				data = dealer.entity()
				
		if data:		
			callback = self.request.get('jsoncallback')
			if callback:
				response = "%s(%s)" % (callback, json.dumps(data))
			else:
				response = json.dumps(data)
			self.response.out.write(response);


class DealerRegionHandler(webapp.RequestHandler):
	def get(self, region=None):
		if region == None:
			self.get()
		else:
			data = []
			query = db.GqlQuery("SELECT * FROM Dealer WHERE region = :1 ORDER BY name", region.upper())
			for dealer in query:					
				data.append(dealer.entity())
			
			callback = self.request.get('jsoncallback')
			if callback:
				response = "%s(%s)" % (callback, json.dumps(data))
			else:
				response = json.dumps(data)
			self.response.out.write(response)