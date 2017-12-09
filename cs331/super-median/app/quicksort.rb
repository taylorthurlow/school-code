module QuickSort
  def quick_sort(array, beg_index, end_index)
    if beg_index < end_index
      pivot_index = qs_partition(array, beg_index, end_index)
      quick_sort(array, beg_index, pivot_index - 1)
      quick_sort(array, pivot_index + 1, end_index)
    end

    return array
  end

  # returns an index of where the pivot ends up
  def qs_partition(array, beg_index, end_index)
    # current_index starts the subarray with larger numbers than the pivot
    current_index = beg_index
    i = beg_index
    while i < end_index
      if array[i] <= array[end_index]
        qs_swap(array, i, current_index)
        current_index += 1
      end
      i += 1
    end
    # after this qs_swap all of the elements before the pivot will be smaller and
    # after the pivot larger
    qs_swap(array, end_index, current_index)

    return current_index
  end

  def qs_swap(array, first_element, second_element)
    temp = array[first_element]
    array[first_element] = array[second_element]
    array[second_element] = temp
  end
end
