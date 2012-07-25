from google.appengine.ext import db
from google.appengine.tools import bulkloader
from models import Dealer

class DealerLoader(bulkloader.Loader):
	def __init__(self):
		bulkloader.Loader.__init__(self, 'Dealer', [
			('name', str),
			('streetAddress', str),
			('city', str),
			('region', str),
			('postalCode', str),
			('phoneNumber', str),
			('url', str),
		])

loaders = [DealerLoader]