package kz.greetgo.sandbox.controller.model;


public class ClientInfo {
  public String id;
  public String fullName;
  public String charm;
  public int age;
  public double totalScore;
  public double maxScore;
  public double minScore;

  @Override
  public String toString() {
    return "ClientInfo{" +
      "id='" + id + '\'' +
      ", fullName='" + fullName + '\'' +
      ", charm='" + charm + '\'' +
      ", age=" + age +
      ", totalScore=" + totalScore +
      ", maxScore=" + maxScore +
      ", minScore=" + minScore +
      '}';
  }
}
