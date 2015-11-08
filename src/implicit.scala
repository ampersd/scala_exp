/**
  * Created by Anton on 11/7/2015.
  */

case class PermissionsList(permissions: Set[String] = Set("UL"));

object PermissionsList {
  implicit def str2permissions(str: String) = PermissionsList(str.split(";").toSet)
  implicit def permissions2str(p: PermissionsList) = p.permissions.mkString(";")
}

//упрощенный
case class User(login: String, permissions: PermissionsList)

object ImplicitObj {
  def main(args: Array[String]) {
    /* somewhere in a galaxy far far away  */
    // фактически, передаем строку в permissions, НО она автоматически преобразуется в Set (благодаря нашим манипуляциям выше)
    val user = User(login = "Vasiliy", permissions = "UL;AL")
    println(user)
  }
}