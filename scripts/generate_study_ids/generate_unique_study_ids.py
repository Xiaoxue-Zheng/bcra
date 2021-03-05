import random as rnd

UQ_IDS_PER_GP = 200

PPT_CODE_MAX = 9999
PPT_CODE_MIN = 0
PPT_CODE_LENGTH = len(str(PPT_CODE_MAX))

MIN_CHARACTER_DIFF = 2

def difference(code1, code2):
	if (len(code1) != len(code2)): raise Exception('Codes must be of equal length') 

	diff = 0
	for i in range(len(code1)):
		if (code1[i] != code2[i]):
			diff+=1

	return diff

def get_new_code(practice_code, prev_code):
	ppt_code = generate_rnd_code()
	if (prev_code == None): return ppt_code

	while(difference(ppt_code, prev_code) < MIN_CHARACTER_DIFF):
		ppt_code = generate_rnd_code()

	return ppt_code

def generate_rnd_code():
	rnd_num = rnd.randint(PPT_CODE_MIN, PPT_CODE_MAX)
	ppt_code = str(rnd_num).zfill(PPT_CODE_LENGTH)
	uq_id = practice_code + '_' + ppt_code
	return uq_id

def save_as_csv(filepath, data):
	f = open(filepath, 'w+')
	for line in data:
		f.write(line + '\n')
	f.close()

print('Please enter a character code to identify a general practice')
practice_code = raw_input('>> ')

uq_ids = [get_new_code(practice_code, None)]
while len(uq_ids) < UQ_IDS_PER_GP:
	uq_id = get_new_code(practice_code, uq_ids[-1])
	if (uq_id not in uq_ids):
		uq_ids.append(uq_id)

filename = practice_code + '_STUDY_IDS.csv'
save_as_csv(filename, uq_ids)
print('{} codes generated'.format(len(uq_ids)))
print('Saved as {}'.format(filename))