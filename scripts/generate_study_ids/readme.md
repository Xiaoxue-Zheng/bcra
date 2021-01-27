# Generate Unique Study IDs

## What does it do?

The `generate_unique_study_ids.py` script is responsible for generating unique IDs for HRYWS study participants.

It does this by first asking the user for a unique code to identify the general practice the IDs are for.

After that, it generates 200 IDs, ensuring no adjacent IDs are too similar.

These IDs are then output into a CSV file, named using the following format: `<GP_CODE>_STUDY_IDS.py`

## How do I run it?

To run it, you must first make sure python (version 2) has been installed onto your machine.

After that has been done, carry out the following commands.

1. Open a command prompt and navigate to the folder containing the script.
```
cd <BCRA_PROJECT_DIR>/scripts/generate_study_ids/
```

2. Run the script using python.
```
python generate_unique_study_ids.py
```

3. When prompted, enter the general practice identifier.

4. This will have generated a CSV in the same directory as the script.