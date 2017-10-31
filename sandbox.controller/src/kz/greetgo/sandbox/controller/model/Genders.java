package kz.greetgo.sandbox.controller.model;


public enum Genders {
  MALE("Мужчина"), FEMALE("Женщина"), NO("Неизвестно");

  private String title;

  Genders(String tytle) {
    this.title = tytle;
  }

  public String getTitle() {
    return title;
  }
}
