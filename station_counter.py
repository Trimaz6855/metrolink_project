
def main():
    station_set = set()
    with open("test_times_large.csv", "r") as f:
        for x in f:
            split_line = x.split(",")
            station_set.add(split_line[0])
            station_set.add(split_line[1])
    
    station_count = 0
    station_set.remove("")

    for i in station_set:
        station_count += 1

    # print(station_set)
    print(len(station_set), "stations")

if __name__ == "__main__":
    main()