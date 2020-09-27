package org.regitiny.tools.magic.impl;

import lombok.extern.log4j.Log4j2;
import org.regitiny.tools.magic.SortMasterMagic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class SortMasterMagicImpl<Type> extends JsonMasterMagicImpl implements SortMasterMagic<Type>
{
  private static final int SORT_ASC = -1;
  private static final int SORT_DESC = 1;

  @Override
  public void quickSortASC(List<Type> objectInput, String sorterName)
  {
    sortby(objectInput, sorterName, SORT_ASC);
  }

  @Override
  public void quickSortDESC(List<Type> objectInput, String sorterName)
  {
    sortby(objectInput, sorterName, SORT_DESC);
  }

  private void sortby(List<Type> objectInput, String sorterName, int sortDesc)
  {
    if (objectInput.isEmpty())
    {
      log.warn("list object input is empty ");
      return;
    }
    List<String> sorter = createSotrer(objectInput, sorterName);
    QuickSort quickSort = new QuickSort();
    quickSort.sort(objectInput, sorter, sortDesc);
  }

  // tiền xử lý tạo mảng để sắp xếp theo tên đã cung cấp
  private List<String> createSotrer(List<Type> objectInput, String sorterName)
  {

    List<String> sorters = new ArrayList<>();
    for (Type object : objectInput)
    {
      try
      {
        Method get = object.getClass().getMethod("get" + sorterName);
        String sorter = (String) get.invoke(object);
        sorters.add(sorter);
      }
      catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
      {
        e.printStackTrace();
      }
    }
    return sorters;
  }


//-----------------------------------------------------------------------------------------------------------


  private class QuickSort
  {
    // mặc định = 1 thì sorter.get(j).compareTo(pivot) * sortDefaultIsDESC > 0  là giảm dần
    private int sortDefaultIsDESC = 1;

    private int partition(List<Type> objects, List<String> sorter, int low, int high)
    {
      String pivot = sorter.get(high);
      int i = (low - 1);
      for (int j = low; j < high; j++)
        if (sorter.get(j).compareTo(pivot) * sortDefaultIsDESC > 0)
          i = swap(objects, sorter, i, j);
      return swap(objects, sorter, i, high);
    }

    private int swap(List<Type> objects, List<String> sorter, int i, int j)
    {
      String temp = sorter.get(++i);
      sorter.set(i, sorter.get(j));
      sorter.set(j, temp);

      Type objecttemp = objects.get(i);
      objects.set(i, objects.get(j));
      objects.set(j, objecttemp);
      return i;
    }

    private void sort(List<Type> objects, List<String> sorter, int low, int high)
    {
      if (low < high)
      {
        int partition = partition(objects, sorter, low, high);
        sort(objects, sorter, low, partition - 1);
        sort(objects, sorter, partition + 1, high);
      }
    }

    private void sort(List<Type> objectsInput, List<String> sorterInput, int sortedByOrder)
    {
      this.sortDefaultIsDESC = sortedByOrder;
      sort(objectsInput, sorterInput, 0, sorterInput.size() - 1);
    }

  }

}