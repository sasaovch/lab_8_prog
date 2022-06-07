package com.ut.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ut.common.data.SpaceMarine;

public final class SortSpaceMarine {
    private SortSpaceMarine() {
        throw new UnsupportedOperationException();
    }

    public static List<SpaceMarine> sortSpaceMarines(String field, String typeFilter, List<SpaceMarine> listSpaceMar) {
        List<SpaceMarine> returnlist;
        if (Objects.isNull(listSpaceMar)) {
            return new ArrayList<SpaceMarine>();
        }
        switch (field) {
            case "id" : returnlist = sortById(listSpaceMar);
                        break;
            case "name" : returnlist = sortByName(listSpaceMar);
                        break;
            case "creation Time" : returnlist = sortByCreationTime(listSpaceMar);
                        break;
            case "x" : returnlist = sortByCoordinateX(listSpaceMar);
                        break;
            case "y" : returnlist = sortByCoordinateY(listSpaceMar);
                        break;
            case "health" : returnlist = sortByHealth(listSpaceMar);
                        break;
            case "heartCount" : returnlist = sortByHeartCount(listSpaceMar);
                        break;
            case "loyal" : returnlist = sortByLoyal(listSpaceMar);
                        break;
            case "category" : returnlist = sortByCategory(listSpaceMar);
                        break;
            case "chapter" : returnlist = sortByChapter(listSpaceMar);
                        break;
            case "parentLegion" : returnlist = sortByParentLegion(listSpaceMar);
                        break;
            case "marinesCount" : returnlist = sortByMarinesCount(listSpaceMar);
                        break;
            default : returnlist = sortByWorld(listSpaceMar);
        }
        if ("decrease".equals(typeFilter)) {
            Collections.reverse(returnlist);
        }
        return returnlist;
    }

    public static List<SpaceMarine> sortById(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(Comparator.comparingLong(SpaceMarine::getID)).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByName(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(Comparator.comparing(SpaceMarine::getName)).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByCreationTime(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(Comparator.comparing(SpaceMarine::getCreationDateTime)).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByCoordinateX(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted((o1, o2) -> (int) (o1.getCoordinates().getX() - o2.getCoordinates().getX())).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByCoordinateY(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(Comparator.comparingLong(s -> s.getCoordinates().getY())).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByHealth(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(Comparator.comparingInt(SpaceMarine::getHealth)).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByHeartCount(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(Comparator.comparingInt(SpaceMarine::getHeartCount)).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByLoyal(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(new Comparator<SpaceMarine>() {
            @Override
            public int compare(SpaceMarine o1, SpaceMarine o2) {
                if (o1.getLoyal() != null && o2.getLoyal() != null)       {
                    return o1.getLoyal().compareTo(o2.getLoyal());
                }
                if (o1.getLoyal() == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByCategory(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(Comparator.comparing(SpaceMarine::getCategory)).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByChapter(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(new Comparator<SpaceMarine>() {
            @Override
            public int compare(SpaceMarine o1, SpaceMarine o2) {
                if (o1.getChapter() != null && o2.getChapter() != null)       {
                    return o1.getChapter().getName().compareTo(o2.getChapter().getName());
                }
                if (o1.getChapter() == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByParentLegion(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(new Comparator<SpaceMarine>() {
            @Override
            public int compare(SpaceMarine o1, SpaceMarine o2) {
                if (o1.getChapter() != null && o2.getChapter() != null)       {
                    return o1.getChapter().getParentLegion().compareTo(o2.getChapter().getParentLegion());
                }
                if (o1.getChapter() == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByMarinesCount(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(new Comparator<SpaceMarine>() {
            @Override
            public int compare(SpaceMarine o1, SpaceMarine o2) {
                if (o1.getChapter() != null && o2.getChapter() != null)       {
                    return (int) (o1.getChapter().getMarinesCount() - o2.getChapter().getMarinesCount());
                }
                if (o1.getChapter() == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> sortByWorld(List<SpaceMarine> listSpaceMar) {
        return listSpaceMar.stream().sorted(new Comparator<SpaceMarine>() {
            @Override
            public int compare(SpaceMarine o1, SpaceMarine o2) {
                if (o1.getChapter() != null && o2.getChapter() != null)       {
                    return o1.getChapter().getWorld().compareTo(o2.getChapter().getWorld());
                }
                if (o1.getChapter() == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
    }
}
