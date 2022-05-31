package com.ut.common.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    private static final long serialVersionUID = -455146270472714858L;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime creationDateTime; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Coordinates coordinates; //Поле не может быть null
    private Integer health; //Поле не может быть null, Значение поля должно быть больше 0
    private Integer heartCount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 3
    private Boolean loyal; //Поле может быть null
    private AstartesCategory category; //Поле не может быть null
    private Chapter chapter; //Поле может быть null
    private String ownerName;

    public SpaceMarine(String name, Coordinates coordinates, Integer health, Integer heartCount, Boolean loyal, AstartesCategory category, Chapter chapter) {
        setName(name);
        setCoordinates(coordinates);
        setHealth(health);
        setHeartCount(heartCount);
        setLoyal(loyal);
        setCategory(category);
        setChapter(chapter);
        creationDateTime = LocalDateTime.now();
    }

    public SpaceMarine() {
        id = 1L;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public Integer getHeartCount() {
        return heartCount;
    }

    public String getName() {
        return name;
    }

    public Integer getHealth() {
        return health;
    }

    public Long getID() {
        return id;
    }

    public Boolean getLoyal() {
        return loyal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(Long setId) {
        this.id = setId;
    }

    public void setCoordinates(Coordinates cor) {
        coordinates = cor;
    }

    public void setTime(LocalDateTime time) {
        creationDateTime = time;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public void setHeartCount(Integer heartCount) {
        this.heartCount = heartCount;
    }

    public void setLoyal(Boolean loyal) {
        this.loyal = loyal;
    }

    public void setCategory(AstartesCategory cat) {
        category = cat;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, health, heartCount, loyal, category, chapter);
    }

    @Override
    public String toString() {
        return "\nName: " + name + "\nId: " + id + "\nHealth: " + health + "\nHeartCount: "
                + heartCount + "\nLoyal: " + loyal + "\nInitialization time: "
                + creationDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + "\n" + "Chapter:\n" + chapter + "\n" + coordinates + "\nCategory: " + category
                + "\n" + "Onwer name: " + ownerName;
    }

    @Override
    public int compareTo(SpaceMarine o) {
        int diffHealth = health - o.getHealth();
        if (diffHealth == 0) {
            int diffHearC = heartCount - o.getHeartCount();
            return diffHearC;
        }
        return diffHealth;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SpaceMarine compSpMar = (SpaceMarine) object;
        return (name.equals(compSpMar.getName())) && (coordinates.equals(compSpMar.getCoordinates()))
                && (health.equals(compSpMar.getHealth())) && (heartCount.equals(compSpMar.getHeartCount()))
                && (Objects.equals(loyal, compSpMar.getLoyal())) && (category.equals(compSpMar.getCategory()))
                && (Objects.equals(chapter, compSpMar.getChapter()));
    }
}
