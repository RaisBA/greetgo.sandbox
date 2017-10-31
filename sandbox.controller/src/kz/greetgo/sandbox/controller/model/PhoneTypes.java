package kz.greetgo.sandbox.controller.model;


public enum PhoneTypes {
  MOBILE("Мобильный"), HOME("Домашний"), WORK("Рабочий");

  private String title;

  PhoneTypes(String tytle) {
    this.title = tytle;
  }

  public String getTitle() {
    return title;
  }
}
