package controllers

import play.api._
import play.api.mvc._
import akka.actor._
import akka.routing._
import workers.{InstrumentationWorker, InstrumentationRequest}

object Application extends Controller {

  val system = ActorSystem("BackgroundTasks")
  val instrumentation = system.actorOf(Props[InstrumentationWorker]
    .withRouter(FromConfig())
    .withDispatcher("task-dispatcher"), 
    name = "instrumentation")
 
  def index = Action {
    val url = "http://www.readwriteweb.com/cloud/2012/04/another-step-towards-the-opens.php"
    Logger.info("Scheduling: " + url)
    instrumentation ! InstrumentationRequest(url)
    Ok(views.html.index("Working..."))
  }
}
