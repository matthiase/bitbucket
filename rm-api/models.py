from google.appengine.ext import db
import copy

class ModelBase(db.Model):
	def entity(self):
		x = copy.deepcopy(self)._entity
		x['id'] = self.key().id()
		return x

class Region(ModelBase):
	name = db.StringProperty(required=True)
	code = db.StringProperty(required=True)	
	country = db.StringProperty(required=True)


class Dealer(ModelBase):
	name = db.StringProperty(required=True)
	streetAddress = db.StringProperty()
	city = db.StringProperty(required=True)
	region = db.StringProperty(required=True)
	postalCode = db.StringProperty()
	phoneNumber = db.StringProperty()
	url = db.StringProperty()
	
		
	