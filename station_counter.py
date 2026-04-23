def main():
    blacklist = ["From ", "To ", "yellow"]
    station_set = set()
    with open("test_times.csv", "r") as f:
        for x in f:
            split_line = x.strip().split(",")
            if (split_line[0] in blacklist) or (split_line[1] in blacklist):
                pass
            else:
                station_set.add(split_line[0])
                station_set.add(split_line[1])  
    station_count = 0

    for i in station_set:
        station_count += 1

    print(sorted(station_set))
    print(len(station_set), "stations")

if __name__ == "__main__":
    main()