package common.errors

trait MLError

case class DatasetEmpty(message: String) extends MLError
