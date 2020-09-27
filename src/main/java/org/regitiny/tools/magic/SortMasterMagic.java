package org.regitiny.tools.magic;

import java.util.List;

public interface SortMasterMagic<Type>
{
  void quickSortASC(List<Type> objectsInput, String sorterName);


  void quickSortDESC(List<Type> objectsInput, String sorterName);
}
