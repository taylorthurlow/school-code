module SuperMedian
  def super_median(list)
    return super_select(list, list.size / 2)
  end

  def super_select(list, selection)
    list_size = list.size
    return adhoc_select(list, selection) if list_size <= 5

    med_size = (list_size % 5).zero? ? (list_size / 5) : (list_size / 5) + 1
    medians = []

    med_size.times do |i|
      last = (5 * i + 4) < list_size ? (5 * i + 4) : (list_size - 1)
      list_for_adhoc_select = list[5 * i..last]
      medians[i] = adhoc_select(list_for_adhoc_select, list_for_adhoc_select.size / 2)
    end

    pivot = super_select(medians, med_size / 2)
    p, q = partition(list, pivot)

    if selection < p
      return super_select(list[0..p - 1], selection)
    elsif selection > q
      return super_select(list[q + 1..list_size - 1], selection - q - 1)
    else
      return pivot
    end
  end

  def partition(list, pivot)
    i = 0
    j = 0
    n = list.size - 1

    while j <= n
      if list[j] < pivot
        swap(list, i, j)
        i += 1
        j += 1
      elsif list[j] > pivot
        swap(list, j, n)
        n -= 1
      else
        j += 1
      end
    end

    return i, n
  end

  # list_size must be <= 5
  def adhoc_select(list, selection)
    sorted_list = adhoc_sort(list)
    return sorted_list[selection]
  end

  # list_size must be <= 5
  def adhoc_sort(list)
    list_size = list.size
    if list_size == 1
      return list
    else
      return send("adhoc_sort_#{list_size}", list)
    end
  end

  def adhoc_sort_2(list)
    if list[0] > list[1]
      return swap(list, 0, 1)
    else
      return list
    end
  end

  def adhoc_sort_3(list)
    list = swap(list, 0, 1) if list[0] > list[1]
    return insert(list[0..1], list[2])
  end

  def adhoc_sort_4(list)
    list = swap(list, 0, 1) if list[0] > list[1]
    list = swap(list, 2, 3) if list[2] > list[3]
    list = list[2..3] + list[0..1] if list[0] > list[2]
    # list is now of form [a, b, c, d] with a < c < d
    return [list[0]] + insert(list[2..3], list[1]) # at most 2 comparisons
  end

  # performs at most 7 comparisons
  def adhoc_sort_5(list)
    list = swap(list, 0, 1) if list[0] > list[1]
    list = swap(list, 2, 3) if list[2] > list[3]
    list = list[2..3] + list[0..1] + [list[4]] if list[0] > list[2]
    # list is now of form [a, b, c, d, e] with a < c < d
    x_list = insert([list[0]] + list[2..3], list[4]) # insert e into [a, c, d]
    # xlist is one of: [a, c, d, e], [a, c, e, d], [a, e, c, d], [e, a, c, d]
    return [x_list[0]] + insert(x_list[1..3], list[1]) # insert b into last 3 elements of x_list
  end

  # performs an ad-hoc insert, list_size is either 2 or 3, performs at most 2 comparisons
  def insert(list, x)
    list_size = list.size
    if x < list[1]
      if x < list[0]
        return [x] + list
      elsif list_size == 2
        return [list[0]] + [x] + [list[1]]
      else
        return [list[0]] + [x] + list[1..2]
      end
    elsif list_size == 2 || x > list[2]
      return list + [x]
    else
      return list[0..1] + [x] + [list[2]]
    end
  end

  def swap(list, i, j)
    list[i], list[j] = list[j], list[i]
    return list
  end
end
